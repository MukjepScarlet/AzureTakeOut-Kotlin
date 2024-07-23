package moe.scarlet.azure_take_out_kt.exception

class BaseException(
    private val type: ExceptionType
) : RuntimeException(type.message) {
    val msg: String
        get() = type.message
}