package com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture

import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse
import java.util.UUID

data class LectureIdResponse(
    val lectureId: String,
    val preSignedUrl: PreSignedUrlResponse,
)
