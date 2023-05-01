package com.example.hierarchical_infolearn.domain.lecture.business.dto.request

import com.example.hierarchical_infolearn.global.file.dto.FileRequest
import javax.validation.Valid
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class CreateLectureRequest(
    @field:Size(min = 1, max = 100, message = "제목은 1 ~ 100자이여야 합니다")
    val title: String,
    @field:Size(min = 1, max = 100, message = "설명은 1 ~ 100자이여야 합니다")
    val explanation: String,
    @field:Pattern(regexp = "^[a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{}|;':\",./<>?\\s]{1,150}\$", message = "searchTitle은 영문자이며 1 ~ 150자이여야 합니다")
    val searchTitle: String,
    @field:Pattern(regexp = "^[a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{}|;':\",./<>?\\s]{1,200}\$", message = "searchExplanation은 영문자이며 1 ~ 200자이여야 합니다")
    val searchExplanation: String,
    val tagNameList: HashSet<String>,
    @field:Valid
    val lectureThumbnail: FileRequest
)
