package com.example.hierarchical_infolearn.global.utils

import com.example.hierarchical_infolearn.domain.user.data.entity.User
import com.example.hierarchical_infolearn.domain.user.data.repo.user.UserRepository
import com.example.hierarchical_infolearn.domain.user.exception.UserNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class CurrentUtil(
    private val userRepository: UserRepository
) {
    fun getCurrentSubject(): String {
        return SecurityContextHolder.getContext().authentication.name
    }

    fun getCurrentUser(): User {
        val subject = getCurrentSubject()
        return userRepository.findById(subject).orElse(null)?: throw UserNotFoundException(subject)
    }
}