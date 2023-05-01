package com.example.hierarchical_infolearn.global.file.type

enum class ImageExt(
    val extension: String,
    val contentType: String
) {
    JPG("jpg", "image/jpeg"),
    JPEG("jpeg", "image/jpeg"),
    PNG("png", "image/png"),
    GIF("gif", "image/gif")
}