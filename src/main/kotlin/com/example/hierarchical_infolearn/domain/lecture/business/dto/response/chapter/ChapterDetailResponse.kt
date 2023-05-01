package com.example.hierarchical_infolearn.domain.lecture.business.dto.response.chapter

import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.video.VideoDetailResponse

data class ChapterDetailResponse(
    val chapterId: Long,
    val title: String,
    val sequence: Int,
    val videos: Set<VideoDetailResponse?>
)