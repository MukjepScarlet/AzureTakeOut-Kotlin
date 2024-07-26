package moe.scarlet.azure_take_out_kt.util

import com.qiniu.common.QiniuException
import com.qiniu.storage.Configuration
import com.qiniu.storage.Region
import com.qiniu.storage.UploadManager
import com.qiniu.util.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import moe.scarlet.azure_take_out_kt.exception.ExceptionType
import moe.scarlet.azure_take_out_kt.extension.isImage
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.property.QiniuProperties
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class QiniuUtil(
    private val qiniuProperties: QiniuProperties
) {

    private val auth = Auth.create(qiniuProperties.accessKey, qiniuProperties.secretKey)
    private val uploadToken = auth.uploadToken(qiniuProperties.bucketName)
    private val cfg = Configuration(Region.huanan()) // 自定义region, 记得改
    private val uploadManager = UploadManager(cfg)

    /**
     * 上传图片, 返回上传后的文件名
     */
    suspend fun uploadImage(file: MultipartFile): String {
        if (file.isEmpty || !file.isImage)
            throw ExceptionType.UPLOAD_FAILED.asException()

        return try {
            withContext (Dispatchers.IO) {
                val response = uploadManager.put(file.bytes, UUID.randomUUID().toString(), uploadToken)
                if (response.isOK && response.isJson)
                    response.jsonToMap()["key"] as String
                else {
                    logger.error(response.bodyString())
                    throw ExceptionType.UPLOAD_FAILED.asException()
                }
            }
        } catch (e: QiniuException) {
            logger.error(e.message)
            logger.error(e.response?.toString())
            throw ExceptionType.UPLOAD_FAILED.asException()
        }
    }

    fun getFullPath(key: String) = if (qiniuProperties.endpoint.endsWith('/'))
        qiniuProperties.endpoint + key
    else
        qiniuProperties.endpoint + '/' + key

}