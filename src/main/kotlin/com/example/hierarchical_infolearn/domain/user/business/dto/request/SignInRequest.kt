package com.example.hierarchical_infolearn.domain.user.business.dto.request

import javax.validation.constraints.NotBlank

data class SignInRequest(

    @field:NotBlank(message = "공백이거나 NULL 값일 수 없습니다")
    val accountId: String,

    val password: String,
)
