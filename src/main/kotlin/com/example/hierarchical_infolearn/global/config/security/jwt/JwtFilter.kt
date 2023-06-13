package com.example.hierarchical_infolearn.global.config.security.jwt

import com.example.hierarchical_infolearn.domain.user.data.entity.common.user.Role
import com.example.hierarchical_infolearn.global.config.security.jwt.auth.StudentDetailsService
import com.example.hierarchical_infolearn.global.config.security.jwt.auth.TeacherDetailsService
import com.example.hierarchical_infolearn.global.error.common.NoAuthenticationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(
    private val tokenProvider: TokenProvider,
    private val studentDetailsService: StudentDetailsService,
    private val teacherDetailsService: TeacherDetailsService,
    private val jwtResolver: JwtResolver
): OncePerRequestFilter() {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val (subject, id) = jwtResolver.getToken(request)?.let { return@let tokenProvider.getSubjectWithExpiredCheck(it) }
            ?: (null to null)
        id?.let {

            val userDetails = when (subject) {
                Role.TEACHER.name -> teacherDetailsService.loadUserByUsername(id)
                Role.STUDENT.name -> studentDetailsService.loadUserByUsername(id)
                else -> throw NoAuthenticationException
            }

            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
        }
        filterChain.doFilter(request, response)
    }
}