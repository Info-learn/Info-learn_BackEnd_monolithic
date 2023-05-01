package com.example.hierarchical_infolearn.global.security.config

import com.example.hierarchical_infolearn.domain.user.data.entity.common.user.Role
import com.example.hierarchical_infolearn.global.security.jwt.TokenProvider
import com.example.hierarchical_infolearn.global.security.jwt.auth.StudentDetailsService
import com.example.hierarchical_infolearn.global.security.jwt.auth.TeacherDetailsService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.firewall.DefaultHttpFirewall
import org.springframework.security.web.firewall.HttpFirewall
import org.springframework.web.cors.CorsUtils


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val tokenProvider: TokenProvider,
    private val studentDetails: StudentDetailsService,
    private val teacherDetails: TeacherDetailsService,
    private val objectMapper: ObjectMapper
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun webSecurityCustomer(): WebSecurityCustomizer {
        return WebSecurityCustomizer {
            it.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**", "/api-docs.json"
                , "/swagger-ui.html", "/infolearn-api-docs/**")}
    }

    @Bean
    fun defaultHttpFirewall(): HttpFirewall {
        return DefaultHttpFirewall()
    }

    @Bean
    @Throws(Exception::class)
    fun configure(httpSecurity: HttpSecurity) : SecurityFilterChain {
        return httpSecurity
            .csrf().disable()
            .formLogin().disable()
            .cors()

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()

            //AUTH
            .antMatchers(HttpMethod.GET, "/api/infolearn/v1/auth/check/account").permitAll()
            .antMatchers(HttpMethod.POST, "/api/infolearn/v1/auth/email").permitAll()
            .antMatchers(HttpMethod.GET, "/api/infolearn/v1/auth/check/code").permitAll()
            .antMatchers(HttpMethod.POST, "/api/infolearn/v1/auth/sign-up/student").permitAll()
            .antMatchers(HttpMethod.POST, "/api/infolearn/v1/auth/sign-up/teacher").permitAll()
            .antMatchers(HttpMethod.POST, "/api/infolearn/v1/auth/sign-in").permitAll()

            //LECTURE
            .antMatchers(HttpMethod.POST, "/api/infolearn/v1/lecture").hasAuthority(Role.TEACHER.name)
            .antMatchers(HttpMethod.GET, "/api/infolearn/v1/lecture").authenticated()
            .antMatchers(HttpMethod.GET,"/api/infolearn/v1/lecture/{{lecture-id}").authenticated()
            .antMatchers(HttpMethod.GET,"/api/infolearn/v1/lecture/search").authenticated()
            .antMatchers(HttpMethod.PUT,"/api/infolearn/v1/lecture/{lecture-id}").hasAuthority(Role.TEACHER.name)
            .antMatchers(HttpMethod.PUT,"/api/infolearn/v1/lecture/{lecture-id}/thumbnail").hasAuthority(Role.TEACHER.name)
            .antMatchers(HttpMethod.DELETE,"/api/infolearn/v1/lecture/{lecture-id}").hasAuthority(Role.TEACHER.name)
            .antMatchers(HttpMethod.PUT,"/api/infolearn/v1/lecture/{lecture-id}/tag").hasAuthority(Role.TEACHER.name)
            .antMatchers(HttpMethod.DELETE,"/api/infolearn/v1/lecture/{lecture-id}/tag").hasAuthority(Role.TEACHER.name)

            //CHAPTER
            .antMatchers(HttpMethod.POST, "/api/infolearn/v1/chapter/{lecture-id}").hasAuthority(Role.TEACHER.name)
            .antMatchers(HttpMethod.DELETE, "/api/infolearn/v1/chapter/{lecture-id}").hasAuthority(Role.TEACHER.name)
            .antMatchers(HttpMethod.PUT, "/api/infolearn/v1/chapter/{lecture-id}/sequence").hasAuthority(Role.TEACHER.name)

            //VIDEO
            .antMatchers(HttpMethod.POST, "/api/infolearn/v1/video/{chapter-id}").hasAuthority(Role.TEACHER.name)
            .antMatchers(HttpMethod.DELETE, "/api/infolearn/v1/video/{chapter-id}").hasAuthority(Role.TEACHER.name)
            .antMatchers(HttpMethod.PUT, "/api/infolearn/v1/video/{lecture-id}/sequence").hasAuthority(Role.TEACHER.name)
            .anyRequest().denyAll()

            .and()
            .apply(FilterConfiguration(tokenProvider, studentDetails, teacherDetails ,objectMapper))
            .and().build()
    }
}