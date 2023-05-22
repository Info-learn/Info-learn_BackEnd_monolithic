package com.example.hierarchical_infolearn.domain.lecture.business.dto.response.video

import com.example.hierarchical_infolearn.domain.lecture.data.entity.common.VideoStatusType

data class VideoMaxResponse(
    val videoId: Long,
    val title: String,
    val hour: Int,
    val minute: Int,
    val second: Int,
    val sequence: Int,
    val status: VideoStatusType?,
    val videoUrl: String,
)