package com.example.hierarchical_infolearn.domain.til.business.dto.request

import com.example.hierarchical_infolearn.global.file.dto.ImageFileRequest
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class UpDateTilRequest(
    @field:NotNull(message = "Null 불가")
    @field:Size(min = 1, max = 100, message = "제목은 1 ~ 50자이여야 합니다")
    var title: String?,

    @field:NotNull(message = "Null 불가")
    @field:Pattern(
        regexp = "^[a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{}|;':\",./<>?\\s]{1,100}\$",
        message = "searchTitle은 영문자이며 1 ~ 100자이여야 합니다"
    )
    var searchTitle: String?,

    var subTitle: String?,

    @field:NotBlank(message = "공백 불가")
    var content: String?,

    @field:NotNull(message = "can't be null")
    var isPrivate: Boolean?,

    @field:NotNull(message = "can't be null")
    var tagNameList: HashSet<String>?,

    @field:Valid
    var tilThumbnail: ImageFileRequest?
){
    constructor(): this(null, null, null, null, null, null, null)
}
