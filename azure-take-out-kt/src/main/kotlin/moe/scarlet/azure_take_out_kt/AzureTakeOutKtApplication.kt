package moe.scarlet.azure_take_out_kt

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("moe.scarlet.azure_take_out_kt.mapper")
class AzureTakeOutKtApplication

fun main(args: Array<String>) {
    runApplication<AzureTakeOutKtApplication>(*args)
}
