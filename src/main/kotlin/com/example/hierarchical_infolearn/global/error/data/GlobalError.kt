package com.example.hierarchical_infolearn.global.error.data

import com.example.hierarchical_infolearn.global.error.ErrorCode

open class GlobalError(
    val errorCode: ErrorCode,
    val data: String
): RuntimeException(errorCode.message) {

}