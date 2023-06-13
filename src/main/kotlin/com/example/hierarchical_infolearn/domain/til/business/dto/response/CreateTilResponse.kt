package com.example.hierarchical_infolearn.domain.til.business.dto.response

import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse
import java.util.UUID

data class CreateTilResponse(

    val tilId: UUID,

    val preSignedUrl: PreSignedUrlResponse? = null
)
