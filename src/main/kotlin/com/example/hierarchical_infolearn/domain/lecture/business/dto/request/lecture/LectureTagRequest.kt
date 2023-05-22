package com.example.hierarchical_infolearn.domain.lecture.business.dto.request.lecture

import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class LectureTagRequest(
    @field:Pattern(regexp = "^\\S+\$", message = "tag는 띄어쓰기가 안됩니다")
    @field:Size(min = 1, max = 20, message = "tag의 길이는 1~20자 입니다")
    val tagId: String
)