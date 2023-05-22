package com.example.hierarchical_infolearn.domain.lecture.business.dto.request.chapter

import com.example.hierarchical_infolearn.global.annotation.ListSizeLimit
import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class ChangeChapterSequenceRequest(
    @field:ListSizeLimit(max = 25, message = "챕터 갯수는 25개 이하이여야 합니다")
    val chapterSequences: List<ChapterSequence>,
) {
    data class ChapterSequence(
        val chapterId: Long,
        @field:Min(1)
        @field:Max(25)
        val sequence: Int,
    )
}
