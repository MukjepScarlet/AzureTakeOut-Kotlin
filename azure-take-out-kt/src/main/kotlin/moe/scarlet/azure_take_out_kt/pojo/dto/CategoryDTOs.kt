package moe.scarlet.azure_take_out_kt.pojo.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

@Schema(description = "新增/修改分类参数")
data class CategoryDTO(
    val id: Long?,
    val name: String,
    val sort: Int,
    val type: Int,
) : Serializable

@Schema(description = "分类分页查询参数")
data class CategoryPageQueryDTO(
    val name: String?,
    val page: Long,
    val pageSize: Long,
    val type: Int?,
) : Serializable
