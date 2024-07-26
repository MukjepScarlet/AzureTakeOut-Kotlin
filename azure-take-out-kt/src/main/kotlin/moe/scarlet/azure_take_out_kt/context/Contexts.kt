package moe.scarlet.azure_take_out_kt.context

import kotlin.reflect.KProperty

// stores

private operator fun <T> ThreadLocal<T>.getValue(t: T?, property: KProperty<*>): T = this.get()

private operator fun <T> ThreadLocal<T>.setValue(t: T?, property: KProperty<*>, value: T) = this.set(value)

var CURRENT_EMPLOYEE_ID by ThreadLocal<Long?>()

var CURRENT_USER_ID by ThreadLocal<Long?>()
