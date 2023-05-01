package com.example.hierarchical_infolearn.global.security.config

import com.example.hierarchical_infolearn.global.error.filter.ExceptionFilter
import com.example.hierarchical_infolearn.global.security.jwt.JwtFilter
import com.example.hierarchical_infolearn.global.security.jwt.TokenProvider
import com.example.hierarchical_infolearn.global.security.jwt.auth.StudentDetailsService
import com.example.hierarchical_infolearn.global.security.jwt.auth.TeacherDetailsService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class FilterConfiguration(
    private val tokenProvider: TokenProvider,
    private val studentDetails: StudentDetailsService,
    private val teacherDetails: TeacherDetailsService,
    private val objectMapper: ObjectMapper
): SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        val jwtFilter = JwtFilter(tokenProvider, studentDetails, teacherDetails)
        val exceptionFilter = ExceptionFilter(objectMapper)
        builder.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(exceptionFilter, JwtFilter::class.java)
    }
}