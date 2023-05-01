package com.example.hierarchical_infolearn.global.file.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class InvalidFileExtension(data: String): GlobalError(ErrorCode.INVALID_FILE_EXTENSION, data) {
}