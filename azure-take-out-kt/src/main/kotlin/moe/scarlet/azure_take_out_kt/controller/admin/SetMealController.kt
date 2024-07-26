package moe.scarlet.azure_take_out_kt.controller.admin

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.pojo.*
import moe.scarlet.azure_take_out_kt.pojo.dto.SetMealDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.SetMealPageQueryDTO
import moe.scarlet.azure_take_out_kt.pojo.vo.SetMealVO
import moe.scarlet.azure_take_out_kt.pojo.vo.SetMealWithDishesVO
import moe.scarlet.azure_take_out_kt.service.SetMealService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/setmeal")
class SetMealController(
    private val setMealService: SetMealService
) {

    @Operation(summary = "更新套餐")
    @PutMapping
    @CacheEvict(value = ["setmeal"], allEntries = true)
    fun update(@RequestBody setMealDTO: SetMealDTO): JsonResult<Nothing> {
        logger.info("更新套餐: $setMealDTO")
        setMealService.update(setMealDTO)
        return JsonResult.success()
    }

    @Operation(summary = "分页查询套餐")
    @GetMapping("/page")
    @Cacheable(value = ["setmeal"])
    fun query(setMealPageQueryDTO: SetMealPageQueryDTO): JsonResult<QueryResult<SetMealVO>> {
        logger.info("分页查询(套餐): $setMealPageQueryDTO")
        return JsonResult.success(setMealService.pageQuery(setMealPageQueryDTO))
    }

    @Operation(summary = "设置套餐状态")
    @PostMapping("/status/{status}")
    @CacheEvict(value = ["setmeal"], allEntries = true)
    fun status(@PathVariable status: Int, id: Long): JsonResult<Nothing> {
        logger.info("套餐状态设置: $status")
        setMealService.status(status, id)
        return JsonResult.success()
    }

    @Operation(summary = "新增套餐")
    @PostMapping
    @CacheEvict(value = ["setmeal"], allEntries = true)
    fun add(@RequestBody setMealDTO: SetMealDTO): JsonResult<Nothing> {
        logger.info("新增套餐: $setMealDTO")
        setMealService.save(setMealDTO)
        return JsonResult.success()
    }

    @Operation(summary = "批量删除套餐")
    @DeleteMapping
    @CacheEvict(value = ["setmeal"], allEntries = true)
    fun delete(@RequestParam ids: List<Long>): JsonResult<Nothing> {
        logger.info("批量删除套餐: $ids")
        setMealService.delete(ids)
        return JsonResult.success()
    }

    @Operation(summary = "按ID查询套餐")
    @GetMapping("/{id}")
    @Cacheable(value = ["setmeal"])
    fun getById(@PathVariable id: Long): JsonResult<SetMealWithDishesVO> =
        JsonResult.success(setMealService.getByIdWithDishes(id))

}