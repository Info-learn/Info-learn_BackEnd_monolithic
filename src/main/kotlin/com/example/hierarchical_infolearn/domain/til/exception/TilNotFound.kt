package com.example.hierarchical_infolearn.domain.til.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class TilNotFound(data: String): GlobalError(ErrorCode.TIL_NOT_FOUND, data) {
}