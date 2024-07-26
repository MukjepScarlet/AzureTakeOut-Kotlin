package moe.scarlet.azure_take_out_kt.controller.admin

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.pojo.*
import moe.scarlet.azure_take_out_kt.pojo.dto.DishDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.DishPageQueryDTO
import moe.scarlet.azure_take_out_kt.pojo.vo.DishVO
import moe.scarlet.azure_take_out_kt.pojo.vo.DishWithFlavorsVO
import moe.scarlet.azure_take_out_kt.service.DishService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/admin/dish")
class DishController(
    private val dishService: DishService
) {

    @Operation(summary = "更新菜品")
    @PutMapping
    fun update(@RequestBody dishDTO: DishDTO): JsonResult<Nothing> {
        logger.info("更新菜品: $dishDTO")
        dishService.update(dishDTO)
        return JsonResult.success()
    }

    @Operation(summary = "分页查询菜品")
    @GetMapping("/page")
    fun query(dishPageQueryDTO: DishPageQueryDTO): JsonResult<QueryResult<DishVO>> {
        logger.info("分页查询(菜品): $dishPageQueryDTO")
        return JsonResult.success(dishService.pageQuery(dishPageQueryDTO))
    }

    @Operation(summary = "设置菜品状态")
    @PostMapping("/status/{status}")
    fun status(@PathVariable status: Int, id: Long): JsonResult<Nothing> {
        logger.info("菜品状态设置: $status")
        dishService.status(status, id)
        return JsonResult.success()
    }

    @Operation(summary = "新增菜品")
    @PostMapping
    fun add(@RequestBody dishDTO: DishDTO): JsonResult<Nothing> {
        logger.info("新增菜品: $dishDTO")
        dishService.save(dishDTO)
        return JsonResult.success()
    }

    @Operation(summary = "批量删除菜品")
    @DeleteMapping
    fun delete(@RequestParam ids: List<Long>): JsonResult<Nothing> {
        logger.info("批量删除菜品: $ids")
        dishService.delete(ids)
        return JsonResult.success()
    }

    @Operation(summary = "按ID查询菜品")
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): JsonResult<DishWithFlavorsVO> =
        JsonResult.success(dishService.getByIdWithFlavor(id))

    @Operation(summary = "按分类查询菜品")
    @GetMapping("/list")
    fun list(categoryId: Long): JsonResult<List<Dish>> = JsonResult.success(dishService.list(categoryId))

}