package com.example.hierarchical_infolearn.domain.til.business.dto.response

import com.example.hierarchical_infolearn.domain.til.data.entity.Til
import com.example.hierarchical_infolearn.domain.til.data.entity.socket.TilUser
import com.example.hierarchical_infolearn.domain.user.data.entity.User
import java.util.UUID

data class TIlResponse(

    val tilId: UUID,

    val title: String,

    val searchTitle: String,

    val subTitle: String?,

    val isPrivate: Boolean,

    val contentId: String,

    val user: TilUserResponse,
) {
    companion object {
        fun of(til: Til, tilUser: TilUser, user: User) = TIlResponse(
            til.id!!,
            til.title,
            til.searchTitle,
            til.subTitle,
            til.isPrivate,
            til.contentId,
            TilUserResponse.of(user, tilUser)
        )
    }
}
