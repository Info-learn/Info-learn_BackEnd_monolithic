package com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture

import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.tag.TagNameResponse
import java.time.LocalDateTime
import java.util.UUID

data class MiniLectureResponse(
    val lectureId: String,
    val title: String,
    val explanation: String,
    val lectureThumbnailUrl: String,
    val tagNameList: Set<TagNameResponse>?,
    val createdAt: LocalDateTime,
    val createdBy: String,
)