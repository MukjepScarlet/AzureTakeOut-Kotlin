package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.constant.StatusConstant
import moe.scarlet.azure_take_out_kt.context.CURRENT_USER_ID
import moe.scarlet.azure_take_out_kt.mapper.AddressBookMapper
import moe.scarlet.azure_take_out_kt.pojo.AddressBook
import moe.scarlet.azure_take_out_kt.pojo.dto.AddressBookDefaultDTO
import moe.scarlet.azure_take_out_kt.service.AddressBookService
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class AddressBookServiceImpl : ServiceImpl<AddressBookMapper, AddressBook>(), AddressBookService {

    private val queryWrapper
        get() = KtQueryWrapper(AddressBook::class.java).eq(AddressBook::userId, CURRENT_USER_ID!!)

    private val updateWrapper
        get() = KtUpdateWrapper(AddressBook::class.java).eq(AddressBook::userId, CURRENT_USER_ID!!)

    override fun listByCurrentUser(): List<AddressBook> =
        this.list(this.queryWrapper)

    override fun getDefaultByCurrentUser(): AddressBook? =
        this.getOneOpt(this.queryWrapper.ne(AddressBook::isDefault, StatusConstant.DISABLE)).getOrNull()

    override fun setDefaultByCurrentUser(addressBookDefaultDTO: AddressBookDefaultDTO) {
        val (id) = addressBookDefaultDTO
        // 去除本来的默认值
        this.update(this.updateWrapper.set(AddressBook::isDefault, StatusConstant.DISABLE))
        // 设置新的默认值
        this.update(this.updateWrapper.eq(AddressBook::id, id).set(AddressBook::isDefault, StatusConstant.ENABLE))
    }

}
