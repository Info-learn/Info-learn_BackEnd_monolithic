package com.example.hierarchical_infolearn.domain.til.business.dto.response

import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse

data class TilContentImageResponse(
    val fileUrl: String,
    val preSignedUrl: PreSignedUrlResponse,
)