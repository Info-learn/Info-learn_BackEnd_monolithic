package com.example.hierarchical_infolearn.global.config.security.jwt.auth

import com.example.hierarchical_infolearn.domain.user.data.repo.user.TeacherRepository
import com.example.hierarchical_infolearn.domain.user.exception.UserNotFoundException
import com.example.hierarchical_infolearn.global.config.security.principle.TeacherDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class TeacherDetailsService (
    private val teacherRepository: TeacherRepository
): UserDetailsService{
    override fun loadUserByUsername(accountId: String): UserDetails {
        if(!teacherRepository.existsById(accountId)) throw UserNotFoundException
        return TeacherDetails(accountId)
    }

}