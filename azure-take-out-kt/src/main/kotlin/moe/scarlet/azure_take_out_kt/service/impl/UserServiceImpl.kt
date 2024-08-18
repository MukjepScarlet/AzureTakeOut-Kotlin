package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.context.CURRENT_USER_ID
import moe.scarlet.azure_take_out_kt.exception.ExceptionType
import moe.scarlet.azure_take_out_kt.extension.selectOne
import moe.scarlet.azure_take_out_kt.mapper.UserMapper
import moe.scarlet.azure_take_out_kt.pojo.User
import moe.scarlet.azure_take_out_kt.pojo.dto.UserDTO
import moe.scarlet.azure_take_out_kt.service.UserService
import moe.scarlet.azure_take_out_kt.util.WeChatUtil
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userMapper: UserMapper,
    private val weChatUtil: WeChatUtil,
) : ServiceImpl<UserMapper, User>(), UserService {

    override val currentUserName: String?
        get() = CURRENT_USER_ID?.let(this::getById)?.name

    override fun getByOpenId(openid: String) = userMapper.selectOne {
        User::openid eq openid
    }

    override suspend fun login(userDTO: UserDTO): User {
        val (code) = userDTO

        // 获取该用户的openid
        val openid = weChatUtil.login(code)?.openid ?: throw ExceptionType.LOGIN_FAILED.asException()

        // 若为新用户, 自动注册
        return getByOpenId(openid) ?: run {
            val newUser = User(0L, openid, null, null, null, null, null)
            this.save(newUser)
            newUser
        }
    }

}