package moe.scarlet.azure_take_out_kt.controller.user

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.pojo.SetMeal
import moe.scarlet.azure_take_out_kt.pojo.SetMealDish
import moe.scarlet.azure_take_out_kt.service.SetMealDishService
import moe.scarlet.azure_take_out_kt.service.SetMealService
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("userSetMealController")
@RequestMapping("/user/setmeal")
class SetMealController(
    private val setMealService: SetMealService,
    private val setMealDishService: SetMealDishService
) {

    @Operation(summary = "按ID查询套餐")
    @GetMapping("/list")
    @Cacheable(value = ["setmeal"])
    fun list(categoryId: Long): JsonResult<List<SetMeal>> =
        JsonResult.success(setMealService.getByCategoryId(categoryId))

    @Operation(summary = "按ID查询套餐包含的菜品")
    @GetMapping("/dish/{id}")
    @Cacheable(value = ["setmeal"])
    fun getById(@PathVariable id: Long): JsonResult<List<SetMealDish>> =
        JsonResult.success(setMealDishService.getBySetMealId(id))

}