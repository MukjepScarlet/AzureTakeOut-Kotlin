package moe.scarlet.azure_take_out_kt.pojo

import com.baomidou.mybatisplus.annotation.*
import moe.scarlet.azure_take_out_kt.constant.StatusConstant
import java.math.BigDecimal
import java.time.LocalDateTime

@TableName("address_book")
data class AddressBook(
    @TableId(type = IdType.AUTO)
    val id: Long,
    val userId: Long,
    val consignee: String?,
    val sex: String?,
    val phone: String,
    val provinceCode: String?,
    val provinceName: String?,
    val cityCode: String?,
    val cityName: String?,
    val districtCode: String?,
    val districtName: String?,
    val detail: String?,
    val label: String?,
    val isDefault: Byte = 0
)

@TableName("category")
data class Category(
    @TableId(type = IdType.AUTO)
    val id: Long,
    val type: Int,
    val name: String,
    val sort: Int = 0,
    val status: Int = StatusConstant.DISABLE,
    @TableField(fill = FieldFill.INSERT)
    val createTime: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    val updateTime: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT)
    val createUser: Long? = null,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    val updateUser: Long? = null
)

@TableName("dish")
data class Dish(
    @TableId(type = IdType.AUTO)
    val id: Long,
    val name: String,
    val categoryId: Long,
    val price: BigDecimal,
    val image: String,
    val description: String,
    val status: Int = 1,
    @TableField(fill = FieldFill.INSERT)
    val createTime: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    val updateTime: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT)
    val createUser: Long? = null,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    val updateUser: Long? = null
)

@TableName("dish_flavor")
data class DishFlavor(
    @TableId(type = IdType.AUTO)
    val id: Long,
    val dishId: Long,
    val name: String,
    val value: String
)

@TableName("employee")
data class Employee(
    @TableId(type = IdType.AUTO)
    val id: Long,
    val name: String,
    val username: String,
    val password: String, // 数据库中存储MD5密文
    val phone: String,
    val sex: String,
    val idNumber: String,
    val status: Int = StatusConstant.ENABLE,
    @TableField(fill = FieldFill.INSERT)
    val createTime: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    val updateTime: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT)
    val createUser: Long? = null,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    val updateUser: Long? = null
)

@TableName("order_detail")
data class OrderDetail(
    @TableId(type = IdType.AUTO)
    val id: Long,
    val name: String?,
    val image: String?,
    val orderId: Long,
    val dishId: Long?,
    val setmealId: Long?,
    val dishFlavor: String?,
    val number: Int = 1,
    val amount: BigDecimal
)

@TableName("orders")
data class Orders(
    @TableId(type = IdType.AUTO)
    val id: Long,
    val number: String?,
    val status: Int = 1,
    val userId: Long,
    val addressBookId: Long,
    val orderTime: LocalDateTime,
    val checkoutTime: LocalDateTime?,
    val payMethod: Int = 1,
    val payStatus: Byte = 0,
    val amount: BigDecimal,
    val remark: String?,
    val phone: String,
    val address: String?,
    val userName: String?,
    val consignee: String?,
    val cancelReason: String?,
    val rejectionReason: String?,
    val cancelTime: LocalDateTime?,
    val estimatedDeliveryTime: LocalDateTime?,
    val deliveryStatus: Byte = 1,
    val deliveryTime: LocalDateTime?,
    val packAmount: Int?,
    val tablewareNumber: Int?,
    val tablewareStatus: Byte = 1,
) {
    object Status {
        const val PENDING_PAYMENT = 1
        const val TO_BE_CONFIRMED = 2
        const val CONFIRMED = 3
        const val DELIVERY_IN_PROGRESS = 4
        const val COMPLETED = 5
        const val CANCELLED = 6
    }

    object PayStatus {
        const val UN_PAID = 0
        const val PAID = 1
        const val REFUND = 2
    }
}

@TableName("setmeal")
data class SetMeal(
    @TableId(type = IdType.AUTO)
    val id: Long,
    val categoryId: Long,
    val name: String,
    val price: BigDecimal,
    val status: Int = 1,
    val description: String,
    val image: String,
    @TableField(fill = FieldFill.INSERT)
    val createTime: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    val updateTime: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT)
    val createUser: Long? = null,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    val updateUser: Long? = null
)

@TableName("setmeal_dish")
data class SetMealDish(
    @TableId(type = IdType.AUTO)
    val id: Long,
    val setmealId: Long,
    val dishId: Long,
    val name: String,
    val price: BigDecimal,
    val copies: Int
)

@TableName("shopping_cart")
data class ShoppingCart(
    @TableId(type = IdType.AUTO)
    val id: Long,
    val name: String?,
    val image: String?,
    val userId: Long,
    val dishId: Long?,
    val setmealId: Long?,
    val dishFlavor: String?,
    val number: Int = 1,
    val amount: BigDecimal,
    @TableField(fill = FieldFill.INSERT)
    val createTime: LocalDateTime? = null,
)

@TableName("user")
data class User(
    @TableId(type = IdType.AUTO)
    val id: Long,
    val openid: String?,
    val name: String?,
    val phone: String?,
    val sex: String?,
    val idNumber: String?,
    val avatar: String?,
    @TableField(fill = FieldFill.INSERT)
    val createTime: LocalDateTime? = null,
)
