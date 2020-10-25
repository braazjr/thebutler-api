package br.com.onsmarttech.thebutler.config

import com.google.common.base.Predicates
import com.google.common.collect.Lists
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.common.OAuth2AccessToken
import springfox.documentation.builders.OAuthBuilder
import springfox.documentation.service.*
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.swagger.web.SecurityConfiguration
import springfox.documentation.swagger.web.SecurityConfigurationBuilder

@Configuration
class OAuthSwaggerSecurityConfig {

    @Value("\${server.host}")
    private val host: String? = null
    @Bean
    fun securityInfo(): SecurityConfiguration {
        return SecurityConfigurationBuilder.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .scopeSeparator(" ")
                .build()
    }

    private fun scopes(): List<AuthorizationScope> {
        return listOf<AuthorizationScope>(
                AuthorizationScope("read", "for read operations"),
                AuthorizationScope("write", "for write operations")
        )
    }

    private fun securityReferences(): List<SecurityReference> {
        return Lists.newArrayList(SecurityReference(SECURITY_SCHEME_NAME, scopes().toTypedArray()))
    }

    @Bean
    fun securityScheme(): SecurityScheme {
        val loginEndpoint = LoginEndpoint("$host/oauth/token")
        val grantType: GrantType = ResourceOwnerPasswordCredentialsGrant("$host/oauth/token")
        return OAuthBuilder().name(SECURITY_SCHEME_NAME)
                .grantTypes(Lists.newArrayList(grantType))
                .scopes(scopes())
                .build()
    }

    @Bean
    fun securityContext(): SecurityContext {
        return SecurityContext.builder()
                .securityReferences(securityReferences())
                .forPaths(Predicates.alwaysTrue())
                .build()
    }

    companion object {
        private const val SECURITY_SCHEME_NAME = "spring_oauth"
        private const val CLIENT_ID = "spring-security-oauth2-read-write-client"
        private const val CLIENT_SECRET = "spring-security-oauth2-read-write-client-password1234"
    }
}