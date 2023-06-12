package com.example.hierarchical_infolearn.domain.til.business.dto.request

import java.util.*
import javax.validation.constraints.NotNull

data class JoinSocketTIlRequest (

    @field:NotNull(message = "공백 불가")
    val isJoinRoom: Boolean,

    @field:NotNull(message = "공백 불가")
    val tilId: UUID,
)
