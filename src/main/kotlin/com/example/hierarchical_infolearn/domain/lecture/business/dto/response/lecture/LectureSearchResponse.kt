package com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture

import org.springframework.data.domain.Page

data class LectureSearchResponse(
    val titleResults: Page<MiniLectureResponse>,
    val explanationResults: Page<MiniLectureResponse>
)