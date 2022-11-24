package de.hsflensburg.recipe_backend.authentication

import de.hsflensburg.recipe_backend.authentication.jwt.AuthEntryPointJwt
import de.hsflensburg.recipe_backend.authentication.jwt.JwtUtils
import de.hsflensburg.recipe_backend.authentication.service.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val userDetailsServiceImpl: UserDetailsServiceImpl,
    private val jwtUtils: JwtUtils,
    private val unauthorizedHandler: AuthEntryPointJwt

) {
    //@Value("\${spring.h2.console.path}")
    //private val h2ConsolePath: String? = null




    @Bean
    fun authenticationJwtTokenFilter(): AuthTokenFilter {
        return AuthTokenFilter(userDetailsServiceImpl, jwtUtils)
    }


    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider? {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsServiceImpl)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationManager(authConfiguration: AuthenticationConfiguration): AuthenticationManager? {

        return authConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    // Von oben nach unten wird die Reihenfolge der Filter festgelegt, oben ist am wichtigsten
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity) : SecurityFilterChain {
        http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers("/api/auth/test/**").authenticated()
            .antMatchers("/api/auth/**").permitAll()

            //.antMatchers("$h2ConsolePath/**").permitAll()
            .anyRequest().authenticated()

        // fix H2 database console: Refused to display ' in a frame because it set 'X-Frame-Options' to 'deny'
        http.headers().frameOptions().sameOrigin()
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}