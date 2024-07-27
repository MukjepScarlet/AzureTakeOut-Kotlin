package moe.scarlet.azure_take_out_kt.pojo.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

@Schema(description = "设置默认地址参数")
data class AddressBookDefaultDTO(
    @JsonProperty("id") // 不加这个会报错, 原因不明
    val id: Long
) : Serializable
