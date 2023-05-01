package com.example.hierarchical_infolearn.domain.lecture.business.dto.response.video

data class VideoDetailResponse(
    val videoId: Long,
    val title: String,
    val playTime: Int,
    val sequence: Int
)
