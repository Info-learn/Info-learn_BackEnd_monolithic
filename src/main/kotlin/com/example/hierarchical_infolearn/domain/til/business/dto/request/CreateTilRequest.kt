package com.example.hierarchical_infolearn.domain.til.business.dto.request

import com.example.hierarchical_infolearn.global.file.dto.ImageFileRequest
import javax.validation.Valid
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class CreateTilRequest(
    @field:Size(min = 1, max = 100, message = "제목은 1 ~ 50자이여야 합니다")
    val title: String,
    @field:Pattern(regexp = "^[a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{}|;':\",./<>?\\s]{1,100}\$", message = "searchTitle은 영문자이며 1 ~ 100자이여야 합니다")
    val searchTitle: String,
    val subTitle: String?,
    val content: String,
    val isPrivate: Boolean,
    val tagNameList: HashSet<String>,
    @field:Valid
    val tilThumbnail: ImageFileRequest?
)
