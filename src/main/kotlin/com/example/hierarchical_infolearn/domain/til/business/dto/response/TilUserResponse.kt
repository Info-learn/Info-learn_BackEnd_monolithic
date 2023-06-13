package com.example.hierarchical_infolearn.domain.til.business.dto.response

import com.example.hierarchical_infolearn.domain.til.data.entity.socket.Role
import com.example.hierarchical_infolearn.domain.til.data.entity.socket.TilUser
import com.example.hierarchical_infolearn.domain.user.data.entity.User

data class TilUserResponse(

    val email: String,

    val accountId: String,

    val nickname: String,

    val role: Role
) {
    companion object {
        fun of (user: User, tilUser: TilUser): TilUserResponse {
            return TilUserResponse(
                user.email,
                user.accountId,
                user.nickname,
                tilUser.role
            )
        }
    }
}