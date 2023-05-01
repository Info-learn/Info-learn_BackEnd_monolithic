package com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CreateChapterRequest(
    @field:NotBlank(message = "lectureId는 NULL이거나 공백일 수 없습니다")
    val lectureId: String,
    @field:NotBlank(message = "챕터 제목은 NULL이거나 공백일 수 없습니다")
    @field:Size(min = 1, max = 100, message = "챕터 제목은 1~100자이여야 합니다")
    val title: String,
    @field:Min(1)
    @field:Max(30)
    val sequence: Int,
)
