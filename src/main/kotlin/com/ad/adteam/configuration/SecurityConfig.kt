package com.ad.adteam.configuration

import com.ad.adteam.security.jwt.AuthEntryPointJwt
import com.ad.adteam.security.jwt.AuthTokenFilter
import com.ad.adteam.service.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig {

    @Autowired
    private val unauthorizedHandler: AuthEntryPointJwt? = null

    @Autowired
    var userDetailsService: UserDetailsServiceImpl? = null

    val apiUsers = "/api/users"
    val allEndpoints = "/**"
    val apiAds = "/api/ads"

    private val AUTHORITY_PREFIX = "ROLE_"
    private val CLAIM_ROLES = "roles"

    private val publicEndpoints = arrayOf(
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/actuator/health",
        "/token/**",
        "/token",
        "/api/auth/**"
    )

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf: CsrfConfigurer<HttpSecurity> ->
                csrf.disable()
            }
            .cors().disable()
            .exceptionHandling()
            .authenticationEntryPoint(unauthorizedHandler)
            .and()
            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity?> ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .authorizeHttpRequests { authorize: AuthorizeHttpRequestsConfigurer<HttpSecurity>
            .AuthorizationManagerRequestMatcherRegistry ->
                authorize
                    .antMatchers(HttpMethod.GET, apiUsers, apiAds)
                    .hasAnyRole("ADMIN", "USER")
                    .antMatchers(HttpMethod.POST, apiUsers, apiAds)
                    .hasAnyRole("ADMIN", "USER")
                    .antMatchers(HttpMethod.PATCH, apiUsers + allEndpoints, apiAds)
                    .hasAnyRole("USER")
                    .antMatchers(
                        HttpMethod.DELETE,
                        apiUsers + allEndpoints, apiAds + allEndpoints
                    )
                    .hasAnyRole("ADMIN", "USER")
                    .antMatchers(
                        HttpMethod.GET,
                        apiUsers + allEndpoints, apiAds + allEndpoints
                    )
                    .hasAnyRole("ADMIN", "USER")
                    .antMatchers(
                        *publicEndpoints
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .getSharedObject(
                AuthenticationManagerBuilder::class.java
            ).userDetailsService(userDetailsService)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationJwtTokenFilter(): AuthTokenFilter {
        return AuthTokenFilter()
    }

    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationManagerBean(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}