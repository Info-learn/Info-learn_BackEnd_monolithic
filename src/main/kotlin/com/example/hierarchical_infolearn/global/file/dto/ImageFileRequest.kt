package com.example.hierarchical_infolearn.global.file.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class ImageFileRequest(
    @field:Pattern(regexp = "^.*\\.(jpg|jpeg|png|heic|webp)$", message = "올바른 확장자가 아닙니다")
    @field:Size(min = 6, max = 200, message = "파일 이름은 최소 6자에서 200자 이여야 합니다")
    val fileName: String,
    @field:Pattern(regexp = "image/(jpg|jpeg|png|heic|webp)$", message = "올바른 Content-Type이 아닙니다")
    val contentType: String,
    @field:Positive(message = "파일 크기는 0보다 커야 합니다")
    @field:Max(value = 10000000, message = "파일 크기는 10MB 이하이여야 합니다")
    val fileSize: Long,
)