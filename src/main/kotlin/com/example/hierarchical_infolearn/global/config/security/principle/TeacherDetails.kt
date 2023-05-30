package com.example.hierarchical_infolearn.global.config.security.principle

import com.example.hierarchical_infolearn.domain.user.data.entity.common.user.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class TeacherDetails(
    private val accountId: String
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(Role.TEACHER.name))
    }

    override fun getPassword(): String? = null

    override fun getUsername(): String = accountId

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}