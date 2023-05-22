package com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video

import com.example.hierarchical_infolearn.global.file.dto.VideoFileRequest
import javax.validation.Valid
import javax.validation.constraints.*

data class CreateVideoRequest(
    @field:NotBlank(message = "영상 제목은 공백이거나 NULL일 수 없습니다")
    @field:Size(min = 1, max = 100, message = "영상 제목은 1 ~ 100자이여야 합니다")
    val title: String,
    @field:PositiveOrZero(message = "영상 시간은 0보다 커야 합니다")
    val playTime: Int,
    @field:Min(1)
    @field:Max(50)
    val sequence: Int,
    @field:Valid
    val videoUrl: VideoFileRequest
)