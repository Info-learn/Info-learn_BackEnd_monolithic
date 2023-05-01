package com.example.hierarchical_infolearn.global.security.jwt.auth

import com.example.hierarchical_infolearn.domain.user.data.repo.user.StudentRepository
import com.example.hierarchical_infolearn.domain.user.exception.UserNotFoundException
import com.example.hierarchical_infolearn.global.security.principle.StudentDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class StudentDetailsService (
    private val studentRepository: StudentRepository
): UserDetailsService{
    override fun loadUserByUsername(accountId: String): UserDetails {
        if(!studentRepository.existsById(accountId)) throw UserNotFoundException(accountId)
        return  StudentDetails(accountId)
    }

}