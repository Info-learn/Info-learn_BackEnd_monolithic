package com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video

import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

data class ChangeVideoRequest(
    val videoId: Long,
    @field:Size(min = 1, max = 100, message = "영상 제목은 1 ~ 100자이여야 합니다")
    val title: String?,
    @field:PositiveOrZero(message = "영상 시간은 0보다 커야 합니다")
    val playTime: Int?,
)