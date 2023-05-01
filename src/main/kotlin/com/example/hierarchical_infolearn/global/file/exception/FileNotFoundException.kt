package com.example.hierarchical_infolearn.global.file.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class FileNotFoundException(data: String): GlobalError(ErrorCode.FILE_NOT_FOUND, data) {
}