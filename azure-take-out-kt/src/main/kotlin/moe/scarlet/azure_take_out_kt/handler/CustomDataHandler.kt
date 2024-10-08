package moe.scarlet.azure_take_out_kt.handler

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import moe.scarlet.azure_take_out_kt.context.CURRENT_EMPLOYEE_ID
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * 无视字段是否为null的更新 (因为更新可能用copy方法产生, 会保留原来的数据)
 */
@Component
class CustomDataHandler : MetaObjectHandler {

    override fun insertFill(metaObject: MetaObject?) {
        metaObject ?: return
        setFieldValByName("createTime", LocalDateTime.now(), metaObject)
        setFieldValByName("updateTime", LocalDateTime.now(), metaObject)
        CURRENT_EMPLOYEE_ID ?: return
        setFieldValByName("createUser", CURRENT_EMPLOYEE_ID, metaObject)
        setFieldValByName("updateUser", CURRENT_EMPLOYEE_ID, metaObject)
    }

    override fun updateFill(metaObject: MetaObject?) {
        metaObject ?: return
        setFieldValByName("updateTime", LocalDateTime.now(), metaObject)
        CURRENT_EMPLOYEE_ID ?: return
        setFieldValByName("updateUser", CURRENT_EMPLOYEE_ID, metaObject)
    }

}