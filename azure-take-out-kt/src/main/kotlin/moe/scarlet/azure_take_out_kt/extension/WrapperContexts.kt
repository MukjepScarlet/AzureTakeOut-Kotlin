package moe.scarlet.azure_take_out_kt.extension

import com.baomidou.mybatisplus.extension.kotlin.AbstractKtWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import kotlin.reflect.KProperty1

data class ConditionalProperty<T, V>(
    val condition: Boolean,
    val property: KProperty1<T, V>,
)

abstract class WrapperContext<T : Any, W : AbstractKtWrapper<T, W>>(open val wrapper: W) {
    infix fun <V> Boolean.then(property: KProperty1<in T, V>) = ConditionalProperty(this, property)

    infix fun <V> ConditionalProperty<in T, V>.eq(value: V?) {
        wrapper.eq(condition, property, value)
    }

    infix fun <V> KProperty1<in T, V>.eq(value: V?) {
        wrapper.eq(this, value)
    }

    infix fun <V> ConditionalProperty<in T, V>.like(value: V?) {
        wrapper.like(condition, property, value)
    }

    infix fun <V> KProperty1<in T, V>.like(value: V?) {
        wrapper.like(this, value)
    }

    infix fun <V> ConditionalProperty<in T, V>.ne(value: V?) {
        wrapper.ne(condition, property, value)
    }

    infix fun <V> KProperty1<in T, V>.ne(value: V?) {
        wrapper.ne(this, value)
    }

    infix fun <V> ConditionalProperty<in T, V>.lt(value: V?) {
        wrapper.lt(condition, property, value)
    }

    infix fun <V> KProperty1<in T, V>.lt(value: V?) {
        wrapper.lt(this, value)
    }

    infix fun <V> ConditionalProperty<in T, V>.le(value: V?) {
        wrapper.le(condition, property, value)
    }

    infix fun <V> KProperty1<in T, V>.le(value: V?) {
        wrapper.le(this, value)
    }

    infix fun <V> ConditionalProperty<in T, V>.gt(value: V?) {
        wrapper.gt(condition, property, value)
    }

    infix fun <V> KProperty1<in T, V>.gt(value: V?) {
        wrapper.gt(this, value)
    }

    infix fun <V> ConditionalProperty<in T, V>.ge(value: V?) {
        wrapper.ge(condition, property, value)
    }

    infix fun <V> KProperty1<in T, V>.ge(value: V?) {
        wrapper.ge(this, value)
    }

}

class QueryWrapperContext<T : Any>(override val wrapper: KtQueryWrapper<T>) :
    WrapperContext<T, KtQueryWrapper<T>>(wrapper) {
    operator fun <V> KProperty1<in T, V>.unaryPlus() {
        wrapper.select(this)
    }
}

class UpdateWrapperContext<T : Any>(override val wrapper: KtUpdateWrapper<T>) :
    WrapperContext<T, KtUpdateWrapper<T>>(wrapper) {

    infix fun <V> KProperty1<in T, V>.set(value: V) {
        wrapper.set(this, value)
    }
}
