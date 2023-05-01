package com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter

import javax.validation.constraints.Size

data class ChangeChapterRequest(
    @field:Size(min = 1, max = 100, message = "제목은 1 ~ 100자이여야 합니다")
    val title: String?,
)