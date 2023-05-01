package com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture

import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.chapter.ChapterDetailResponse
import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.tag.TagNameResponse
import java.util.UUID

data class MaxLectureResponse(
    val lectureId: String,
    val title: String,
    val explanation: String,
    val lectureThumbnailUrl: String,
    val tagNameList: Set<TagNameResponse>?,
    val chapters: Set<ChapterDetailResponse>?
)