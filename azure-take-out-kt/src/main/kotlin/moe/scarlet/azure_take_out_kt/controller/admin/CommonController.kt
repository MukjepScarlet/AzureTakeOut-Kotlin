package moe.scarlet.azure_take_out_kt.controller.admin

import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.util.QiniuUtil
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/admin/common")
class CommonController(
    private val qiniuUtil: QiniuUtil
) {

    @PostMapping("/upload")
    fun upload(file: MultipartFile): JsonResult<String> {
        return JsonResult.success(qiniuUtil.getFullPath(qiniuUtil.uploadImage(file)))
    }

}