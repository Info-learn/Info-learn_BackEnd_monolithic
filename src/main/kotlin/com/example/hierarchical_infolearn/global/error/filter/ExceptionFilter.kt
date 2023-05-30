package com.example.hierarchical_infolearn.global.error.filter

import com.example.hierarchical_infolearn.global.error.data.ErrorResponse
import com.example.hierarchical_infolearn.global.error.data.GlobalException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: GlobalException) {
            response.let {
                it.status = e.errorCode.status.value()
                it.contentType = MediaType.APPLICATION_JSON_VALUE
                it.characterEncoding = "UTF-8"
            }
            objectMapper.writeValue(response.writer, ErrorResponse.of(e.errorCode))
        }
    }


}