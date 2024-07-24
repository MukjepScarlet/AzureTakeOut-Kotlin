package moe.scarlet.azure_take_out_kt.handler

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import moe.scarlet.azure_take_out_kt.context.CURRENT_EMPLOYEE_ID
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CustomDataHandler : MetaObjectHandler {

    override fun insertFill(metaObject: MetaObject?) {
        metaObject ?: return
        setFieldValByName("createTime", LocalDateTime.now(), metaObject)
        setFieldValByName("updateTime", LocalDateTime.now(), metaObject)
        setFieldValByName("createUser", CURRENT_EMPLOYEE_ID, metaObject)
        setFieldValByName("updateUser", CURRENT_EMPLOYEE_ID, metaObject)
    }

    override fun updateFill(metaObject: MetaObject?) {
        metaObject ?: return
        setFieldValByName("updateTime", LocalDateTime.now(), metaObject)
        setFieldValByName("updateUser", CURRENT_EMPLOYEE_ID, metaObject)
    }

}