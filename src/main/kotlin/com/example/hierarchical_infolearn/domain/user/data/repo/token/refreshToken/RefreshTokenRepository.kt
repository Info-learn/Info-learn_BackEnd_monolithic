package com.example.hierarchical_infolearn.domain.user.data.repo.token.refreshToken

import com.example.hierarchical_infolearn.domain.user.data.entity.token.refreshToken.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository: CrudRepository<RefreshToken, String> {
}