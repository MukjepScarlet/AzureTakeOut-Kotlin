package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.context.CURRENT_USER_ID
import moe.scarlet.azure_take_out_kt.mapper.ShoppingCartMapper
import moe.scarlet.azure_take_out_kt.pojo.ShoppingCart
import moe.scarlet.azure_take_out_kt.pojo.dto.ShoppingCartDTO
import moe.scarlet.azure_take_out_kt.service.DishService
import moe.scarlet.azure_take_out_kt.service.SetMealService
import moe.scarlet.azure_take_out_kt.service.ShoppingCartService
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ShoppingCartServiceImpl(
    private val dishService: DishService,
    private val setMealService: SetMealService
) : ServiceImpl<ShoppingCartMapper, ShoppingCart>(), ShoppingCartService {

    /**
     * 判断当前用户的购物车项目 (0条或1条)
     */
    private val ShoppingCartDTO.wrapper
        get() = KtQueryWrapper(ShoppingCart::class.java)
            .eq(ShoppingCart::userId, CURRENT_USER_ID!!)
            .eq(setmealId != null, ShoppingCart::setmealId, setmealId)
            .eq(dishId != null, ShoppingCart::dishId, dishId)
            .eq(dishFlavor != null, ShoppingCart::dishFlavor, dishFlavor)

    override fun sub(shoppingCartDTO: ShoppingCartDTO) {
        this.remove(shoppingCartDTO.wrapper)
    }

    override fun listByCurrentUser(): List<ShoppingCart> =
        this.list(KtQueryWrapper(ShoppingCart::class.java).eq(ShoppingCart::userId, CURRENT_USER_ID!!))

    override fun add(shoppingCartDTO: ShoppingCartDTO) {
        val (dishFlavor, dishId, setmealId) = shoppingCartDTO

        val target = this.getOneOpt(shoppingCartDTO.wrapper).getOrNull()
        if (target != null) {
            // 若本人在购物车中添加过此商品(套餐/菜品), number++
            this.updateById(target.copy(number = target.number + 1))
        } else {
            // 否则插入
            this.save(
                if (setmealId != null) { // 是套餐
                    val (_, _, name, price, _, _, image) = setMealService.getById(setmealId)
                    ShoppingCart(0L, name, image, CURRENT_USER_ID!!, null, setmealId, null, 1, price)
                } else { // 是单独的菜品
                    val (_, name, _, price, image) = dishService.getById(dishId)
                    ShoppingCart(0L, name, image, CURRENT_USER_ID!!, dishId, null, dishFlavor, 1, price)
                }
            )
        }
    }

    override fun cleanByCurrentUser() {
        this.remove(KtQueryWrapper(ShoppingCart::class.java).eq(ShoppingCart::userId, CURRENT_USER_ID!!))
    }

}