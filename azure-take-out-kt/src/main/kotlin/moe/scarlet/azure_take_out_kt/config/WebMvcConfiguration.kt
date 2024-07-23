package moe.scarlet.azure_take_out_kt.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.interceptor.JwtTokenAdminInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "碧蓝外卖 API 文档",
        version = "1.0",
        description = "API 以旧换新",
        contact = Contact(
            name = "木葉 Scarlet",
            url = "https://github.com/MukjepScarlet/AzureTakeOut-Kotlin"
        )
    )
)
class WebMvcConfiguration(
    private val jwtTokenAdminInterceptor: JwtTokenAdminInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        logger.info("开始注册自定义拦截器...")
        registry.addInterceptor(jwtTokenAdminInterceptor)
            .addPathPatterns("/admin/**")
            .excludePathPatterns("/admin/employee/login")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        logger.info("开始设置静态资源映射...");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/")
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
    }
}