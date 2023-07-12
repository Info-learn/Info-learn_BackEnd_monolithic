package com.example.hierarchical_infolearn.domain.til.business.dto.request

import java.util.*
import javax.validation.constraints.NotBlank


data class JoinSocketTIlRequest (

    @field:NotBlank(message = "공백 불가")
    var tilId: UUID?
) {
    constructor(): this(null)
}