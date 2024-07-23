package moe.scarlet.azure_take_out_kt.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "azure.alioss")
data class AliOssProperties(
    var endpoint: String = "",
    var accessKeyId: String = "",
    var accessKeySecret: String = "",
    var bucketName: String = "",
)

@Component
@ConfigurationProperties(prefix = "azure.jwt")
data class JwtProperties(
    var adminSecretKey: String = "",
    var adminTTL: Long = 0L,
    var adminTokenName: String = "",
    var userSecretKey: String = "",
    var userTtl: Long = 0L,
    var userTokenName: String = "",
)

@Component
@ConfigurationProperties(prefix = "azure.wechat")
data class WeChatProperties(
    var appid: String = "", //小程序的appid
    var secret: String = "", //小程序的秘钥
    var mchid: String = "", //商户号
    var mchSerialNo: String = "", //商户API证书的证书序列号
    var privateKeyFilePath: String = "", //商户私钥文件
    var apiV3Key: String = "", //证书解密的密钥
    var weChatPayCertFilePath: String = "", //平台证书
    var notifyUrl: String = "", //支付成功的回调地址
    var refundNotifyUrl: String = "", //退款成功的回调地址
)