package moe.scarlet.azure_take_out_kt.controller.admin

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.pojo.Category
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.pojo.QueryResult
import moe.scarlet.azure_take_out_kt.pojo.dto.CategoryDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.CategoryPageQueryDTO
import moe.scarlet.azure_take_out_kt.service.CategoryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/category")
class CategoryController(
    private val categoryService: CategoryService
) {

    @Operation(summary = "更新分类")
    @PutMapping
    fun update(@RequestBody categoryDTO: CategoryDTO): JsonResult<Nothing> {
        logger.info("更新分类: $categoryDTO")
        categoryService.update(categoryDTO)
        return JsonResult.success()
    }

    @Operation(summary = "分页查询分类")
    @GetMapping("/page")
    fun query(categoryPageQueryDTO: CategoryPageQueryDTO): JsonResult<QueryResult<Category>> {
        logger.info("分页查询(分类): $categoryPageQueryDTO")
        return JsonResult.success(categoryService.pageQuery(categoryPageQueryDTO))
    }

    @Operation(summary = "设置分类状态")
    @PostMapping("/status/{status}")
    fun status(@PathVariable status: Int, id: Long): JsonResult<Nothing> {
        logger.info("分类状态设置: $status")
        categoryService.status(status, id)
        return JsonResult.success()
    }

    @Operation(summary = "新增分类")
    @PostMapping
    fun add(@RequestBody categoryDTO: CategoryDTO): JsonResult<Nothing> {
        logger.info("新增分类: $categoryDTO")
        categoryService.save(categoryDTO)
        return JsonResult.success()
    }

    @Operation(summary = "删除分类")
    @DeleteMapping
    fun delete(id: Long): JsonResult<Nothing> {
        logger.info("删除分类: $id")
        categoryService.removeById(id)
        return JsonResult.success()
    }

    @Operation(summary = "查询指定类型分类")
    @GetMapping("/list")
    fun list(type: Int) = JsonResult.success(categoryService.list(type))

}