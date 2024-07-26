package moe.scarlet.azure_take_out_kt.controller.user

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.pojo.ShoppingCart
import moe.scarlet.azure_take_out_kt.pojo.dto.ShoppingCartDTO
import moe.scarlet.azure_take_out_kt.service.ShoppingCartService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user/shoppingCart")
class ShoppingCartController(
    private val shoppingCartService: ShoppingCartService
) {

    @Operation(summary = "删除购物车中一个商品")
    @PostMapping("/sub")
    fun sub(@RequestBody shoppingCartDTO: ShoppingCartDTO): JsonResult<Nothing> {
        logger.info("删除购物车商品: $shoppingCartDTO")
        shoppingCartService.sub(shoppingCartDTO)
        return JsonResult.success()
    }

    @Operation(summary = "查看购物车")
    @GetMapping("/list")
    fun list(): JsonResult<List<ShoppingCart>> {
        logger.info("查看购物车")
        return JsonResult.success(shoppingCartService.listByCurrentUser())
    }

    @Operation(summary = "添加购物车")
    @PostMapping("/add")
    fun add(@RequestBody shoppingCartDTO: ShoppingCartDTO): JsonResult<Nothing> {
        logger.info("添加购物车商品: $shoppingCartDTO")
        shoppingCartService.add(shoppingCartDTO)
        return JsonResult.success()
    }

    @Operation(summary = "清空购物车")
    @DeleteMapping("/clean")
    fun clean(): JsonResult<Nothing> {
        logger.info("清空购物车")
        shoppingCartService.cleanByCurrentUser()
        return JsonResult.success()
    }

}