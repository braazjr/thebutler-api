package br.com.onsmarttech.thebutler.config

import com.google.common.base.Predicate
import com.google.common.collect.Lists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.service.SecurityScheme
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Autowired
    private val securityScheme: SecurityScheme? = null

    @Autowired
    private val securityContext: SecurityContext? = null
    private val COMPANY_API: Predicate<String> = PathSelectors.ant("/api/**")
    private val OAUTH_API: Predicate<String> = PathSelectors.ant("/oauth/**")

    @Bean
    fun companyApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .groupName("The Butler API")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(COMPANY_API)
                .build()
                .securitySchemes(Lists.newArrayList(securityScheme))
                .securityContexts(Lists.newArrayList(securityContext))
    }

    @Bean
    fun authenticationApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .groupName("OAuth 2.0 API")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(OAUTH_API)
                .build()
    }

    private fun apiInfo(): ApiInfo {
        val contact = Contact("Elias Braz", "", "developer@onsmarttech.com.br")
        return ApiInfoBuilder()
                .title("The Butler API")
                .description("A API to On Smart Tech Applications")
                .version("1.0")
//                .license("Apache 2.0")
//                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact)
                .build()
    }
}