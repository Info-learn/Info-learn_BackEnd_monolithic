package com.example.hierarchical_infolearn.domain.lecture.business.dto.request.video

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class ChangeVideoSequenceRequest(
    val videoSequences: List<VideoSequence>,
) {
    data class VideoSequence(
        val videoId: Long,
        @field:Min(1)
        @field:Max(50)
        val sequence: Int,
    )
}
