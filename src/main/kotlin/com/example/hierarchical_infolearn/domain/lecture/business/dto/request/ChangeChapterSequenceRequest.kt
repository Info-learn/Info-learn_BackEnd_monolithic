package com.example.hierarchical_infolearn.domain.lecture.business.dto.request

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class ChangeChapterSequenceRequest(
    val chapterId: Long,
    @field:Min(1)
    @field:Max(30)
    val sequence: Int,
)
