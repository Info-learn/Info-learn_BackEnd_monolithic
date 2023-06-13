package com.example.hierarchical_infolearn.domain.til.business.dto.request

import com.example.hierarchical_infolearn.global.file.dto.ImageFileRequest
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class UpDateTilRequest (
    @field:NotBlank(message = "공백 불가")
    val id: UUID?,
    @field:Size(min = 1, max = 100, message = "제목은 1 ~ 50자이여야 합니다")
    val title: String,
    @field:Pattern(regexp = "^[a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{}|;':\",./<>?\\s]{1,100}\$", message = "searchTitle은 영문자이며 1 ~ 100자이여야 합니다")
    val searchTitle: String,
    val subTitle: String?,
    @field:NotBlank(message = "공백 불가")
    val content: String?,
    @field:NotNull(message = "can't be null")
    val isPrivate: Boolean?,
    @field:NotNull(message = "can't be null")
    val tagNameList: HashSet<String>,
    @field:Valid
    val tilThumbnail: ImageFileRequest?
)
