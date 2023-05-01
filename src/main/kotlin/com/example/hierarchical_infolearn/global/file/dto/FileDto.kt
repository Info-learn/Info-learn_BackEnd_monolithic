package com.example.hierarchical_infolearn.global.file.dto

import com.example.hierarchical_infolearn.global.file.type.FileContentType


data class FileDto(
    var fileUrl: String,
    val fileType: FileContentType,
    val extension: String,
    val fileName: String
) {
    fun removeParameter(){
        this.fileUrl = this.fileUrl.substring(0, this.fileUrl.lastIndexOf('?'))
    }
}
