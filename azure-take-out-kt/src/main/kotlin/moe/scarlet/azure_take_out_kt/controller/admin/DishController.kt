package moe.scarlet.azure_take_out_kt.controller.admin

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.pojo.Dish
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.pojo.QueryResult
import moe.scarlet.azure_take_out_kt.pojo.dto.DishDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.DishPageQueryDTO
import moe.scarlet.azure_take_out_kt.pojo.vo.DishVO
import moe.scarlet.azure_take_out_kt.pojo.vo.DishWithFlavorsVO
import moe.scarlet.azure_take_out_kt.service.DishService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/dish")
class DishController(
    private val dishService: DishService
) {

    @Operation(summary = "更新菜品")
    @PutMapping
    @CacheEvict(value = ["dish"], allEntries = true)
    fun update(@RequestBody dishDTO: DishDTO): JsonResult<Nothing> {
        logger.info("更新菜品: $dishDTO")
        dishService.update(dishDTO)
        return JsonResult.success()
    }

    @Operation(summary = "分页查询菜品")
    @GetMapping("/page")
    @Cacheable(value = ["dish"])
    fun query(dishPageQueryDTO: DishPageQueryDTO): JsonResult<QueryResult<DishVO>> {
        logger.info("分页查询(菜品): $dishPageQueryDTO")
        return JsonResult.success(dishService.pageQuery(dishPageQueryDTO))
    }

    @Operation(summary = "设置菜品状态")
    @PostMapping("/status/{status}")
    @CacheEvict(value = ["dish"], allEntries = true)
    fun status(@PathVariable status: Int, id: Long): JsonResult<Nothing> {
        logger.info("菜品状态设置: $status")
        dishService.status(status, id)
        return JsonResult.success()
    }

    @Operation(summary = "新增菜品")
    @PostMapping
    @CacheEvict(value = ["dish"], allEntries = true)
    fun add(@RequestBody dishDTO: DishDTO): JsonResult<Nothing> {
        logger.info("新增菜品: $dishDTO")
        dishService.save(dishDTO)
        return JsonResult.success()
    }

    @Operation(summary = "批量删除菜品")
    @DeleteMapping
    @CacheEvict(value = ["dish"], allEntries = true)
    fun delete(@RequestParam ids: List<Long>): JsonResult<Nothing> {
        logger.info("批量删除菜品: $ids")
        dishService.delete(ids)
        return JsonResult.success()
    }

    @Operation(summary = "按ID查询菜品")
    @GetMapping("/{id}")
    @Cacheable(value = ["dish"])
    fun getById(@PathVariable id: Long): JsonResult<DishWithFlavorsVO> =
        JsonResult.success(dishService.getByIdWithFlavor(id))

    @Operation(summary = "按分类查询菜品")
    @GetMapping("/list")
    @Cacheable(value = ["dish"])
    fun list(categoryId: Long): JsonResult<List<Dish>> = JsonResult.success(dishService.list(categoryId))

}