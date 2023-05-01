package com.example.hierarchical_infolearn.domain.user.data.repo.user

import com.example.hierarchical_infolearn.domain.user.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, String> {

    fun existsByAccountId(accountId: String): Boolean

    fun existsByEmail(email: String): Boolean
}