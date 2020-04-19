package br.com.onsmarttech.thebutler.security

import br.com.onsmarttech.thebutler.config.TheButlerProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableResourceServer
class ResourceServerConfig : ResourceServerConfigurerAdapter() {

    @Autowired
    private lateinit var theButlerProperties: TheButlerProperties

    override fun configure(http: HttpSecurity?) {
        http!!
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/fichas/**/for-jasper")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/usuarios")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
    }

    override fun configure(resources: ResourceServerSecurityConfigurer?) {
        resources!!.stateless(true)
    }

    @Bean
    fun createExpressionHandler(): MethodSecurityExpressionHandler? {
        return OAuth2MethodSecurityExpressionHandler()
    }

    @Bean
    fun corsFilter(): CorsFilter? {
        val configuration = CorsConfiguration()
        configuration.allowCredentials = true
        configuration.addAllowedHeader("*")
        configuration.addAllowedMethod("*")
        configuration.maxAge = 3600L
        configuration.allowedOrigins = theButlerProperties.originsPermitidas
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return CorsFilter(source)
    }
}