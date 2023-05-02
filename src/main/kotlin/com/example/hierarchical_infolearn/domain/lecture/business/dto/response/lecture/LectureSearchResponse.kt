package com.example.hierarchical_infolearn.domain.lecture.business.dto.response.lecture

data class LectureSearchResponse(
    val titleResults: List<MiniLectureResponse>,
    val explanationResults: List<MiniLectureResponse>
)