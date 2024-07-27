package moe.scarlet.azure_take_out_kt.controller.user

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.context.CURRENT_USER_ID
import moe.scarlet.azure_take_out_kt.exception.ExceptionType
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.pojo.AddressBook
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.pojo.dto.AddressBookDefaultDTO
import moe.scarlet.azure_take_out_kt.service.AddressBookService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user/addressBook")
class AddressBookController(
    private val addressBookService: AddressBookService
) {

    @Operation(summary = "新增地址")
    @PostMapping
//    @CacheEvict(value = ["addressBook"], allEntries = true)
    fun save(@RequestBody addressBook: AddressBook): JsonResult<Nothing> {
        logger.info("新增地址: $addressBook")
        addressBookService.save(addressBook.copy(userId = CURRENT_USER_ID!!))
        return JsonResult.success()
    }

    @Operation(summary = "修改地址")
    @PutMapping
//    @CacheEvict(value = ["addressBook"], allEntries = true)
    fun update(@RequestBody addressBook: AddressBook): JsonResult<Nothing> {
        logger.info("修改地址: $addressBook")
        addressBookService.updateById(addressBook)
        return JsonResult.success()
    }

    @Operation(summary = "查询地址")
    @GetMapping
//    @Cacheable(value = ["addressBook"])
    fun getById(id: Long): JsonResult<AddressBook> {
        logger.info("查询地址: $id")
        return JsonResult.success(addressBookService.getById(id))
    }

    @Operation(summary = "删除地址")
    @DeleteMapping
//    @CacheEvict(value = ["addressBook"], allEntries = true)
    fun remove(id: Long): JsonResult<Nothing> {
        logger.info("删除地址: $id")
        addressBookService.removeById(id)
        return JsonResult.success()
    }

    @Operation(summary = "查询当前用户所有地址")
    @GetMapping("/list")
//    @Cacheable(value = ["addressBook"])
    fun list(): JsonResult<List<AddressBook>> { // 文档返回类型不对
        logger.info("查询当前用户所有地址")
        return JsonResult.success(addressBookService.listByCurrentUser())
    }

    @Operation(summary = "设置默认地址")
    @PutMapping("/default")
//    @CacheEvict(value = ["addressBook"], allEntries = true)
    fun default(@RequestBody addressBookDefaultDTO: AddressBookDefaultDTO): JsonResult<Nothing> {
        logger.info("设置默认地址: $addressBookDefaultDTO")
        addressBookService.setDefaultByCurrentUser(addressBookDefaultDTO)
        return JsonResult.success()
    }

    @Operation(summary = "查询默认地址")
    @GetMapping("/default")
//    @Cacheable(value = ["addressBook"])
    fun default(): JsonResult<AddressBook?> {
        logger.info("查询默认地址")
        return JsonResult.success(addressBookService.getDefaultByCurrentUser())
    }

}
