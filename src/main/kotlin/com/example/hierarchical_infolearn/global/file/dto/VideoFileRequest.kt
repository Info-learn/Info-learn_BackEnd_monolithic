package com.example.hierarchical_infolearn.global.file.dto

import javax.validation.constraints.Pattern

data class VideoFileRequest(
    @field:Pattern(regexp = "^.*\\.(mp4)\$", message = "올바른 파일명이 아닙니다")
    val fileName: String,
    @field:Pattern(regexp = "^\\s*([a-z]+)/([a-z0-9\\-+]+)\\s*(?:;(.*))?\$", message = "올바른 Content-Type이 아닙니다")
    val contentType: String,
)