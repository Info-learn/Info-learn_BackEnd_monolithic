package com.example.hierarchical_infolearn.global.utils

import com.corundumstudio.socketio.SocketIOClient
import com.example.hierarchical_infolearn.domain.user.data.entity.User
import com.example.hierarchical_infolearn.domain.user.data.repo.user.UserRepository
import com.example.hierarchical_infolearn.domain.user.exception.UserNotFoundException
import com.example.hierarchical_infolearn.global.config.security.jwt.JwtResolver
import com.example.hierarchical_infolearn.global.config.security.jwt.TokenProvider
import com.example.hierarchical_infolearn.global.error.common.NoAuthenticationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class CurrentUtil(
    private val userRepository: UserRepository,
    private val jwtProvider: TokenProvider,
    private val jwtResolver: JwtResolver,
) {
    fun getCurrentSubject(): String {
        return SecurityContextHolder.getContext().authentication.name
    }

    fun getCurrentUser(): User {
        val subject = getCurrentSubject()
        return userRepository.findByIdOrNull(subject)?: throw UserNotFoundException
    }

    fun getCurrentUser(socketIOClient: SocketIOClient) = getUserByAccountId(
        jwtProvider.getSubjectWithExpiredCheck(jwtResolver.resolveToken(socketIOClient)).second
    ) ?: throw NoAuthenticationException

    fun getUserByAccountId(accountId: String) = userRepository.findByIdOrNull(accountId)
}