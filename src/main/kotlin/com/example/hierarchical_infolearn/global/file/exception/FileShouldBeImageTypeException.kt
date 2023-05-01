package com.example.hierarchical_infolearn.global.file.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError
import com.example.hierarchical_infolearn.global.file.type.ImageExt

class FileShouldBeImageTypeException(): GlobalError(ErrorCode.FILE_SHOULD_BE_IMAGE_TYPE, ImageExt.values().map {it.extension}.toString()) {
}