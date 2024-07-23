package moe.scarlet.azure_take_out_kt.config

import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.interceptor.JwtTokenAdminInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class WebMvcConfiguration(
    private val jwtTokenAdminInterceptor: JwtTokenAdminInterceptor
) : WebMvcConfigurationSupport() {

    override fun addInterceptors(registry: InterceptorRegistry) {
        logger.info("开始注册自定义拦截器...")
        registry.addInterceptor(jwtTokenAdminInterceptor)
            .addPathPatterns("/admin/**")
            .excludePathPatterns("/admin/employee/login")
    }

    @Bean
    fun docket(): Docket {
        val apiInfo = ApiInfoBuilder()
            .title("苍穹外卖项目接口文档")
            .version("3.0")
            .description("苍穹外卖项目接口文档")
            .build()
        val docket = Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .select()
            .apis(RequestHandlerSelectors.basePackage("more.scarlet.azure_take_out_kt.controller"))
            .paths(PathSelectors.any())
            .build()
        return docket
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/")
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
    }
}