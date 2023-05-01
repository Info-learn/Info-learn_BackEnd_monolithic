package com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video

data class ChangeVideoChapterRequest(
    val chapterId: Long,
    val targetChapterId: Long,
)
