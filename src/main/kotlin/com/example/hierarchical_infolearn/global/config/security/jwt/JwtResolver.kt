package com.example.hierarchical_infolearn.global.config.security.jwt

import com.corundumstudio.socketio.SocketIOClient
import com.example.hierarchical_infolearn.global.config.security.jwt.exception.InvalidTokenException
import com.example.hierarchical_infolearn.global.error.common.NoAuthenticationException
import org.springframework.stereotype.Component
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest

@Component
class JwtResolver {

    companion object {
        const val BEARER = "Bearer "
        const val AUTH = "auth"
        const val REGEX = "[(a-zA-Z0-9-._~+/=*)]{30,600}"
    }

    fun getToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(JwtFilter.AUTH) ?: return null
        if (bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7)
        }
        throw InvalidTokenException
    }

    fun resolveToken(socketIOClient: SocketIOClient) = parseToken(socketIOClient.handshakeData.urlParams[AUTH])

    private fun parseToken(token: List<String>?): String {
        if (!token.isNullOrEmpty() && Pattern.matches(REGEX, token[0])) {
            return token[0]
        } else {
            throw NoAuthenticationException
        }
    }
}