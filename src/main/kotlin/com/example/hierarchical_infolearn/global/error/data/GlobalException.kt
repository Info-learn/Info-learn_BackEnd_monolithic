package com.example.hierarchical_infolearn.global.error.data

import com.example.hierarchical_infolearn.global.error.ErrorCode

open class GlobalException(
    val errorCode: ErrorCode
): RuntimeException(errorCode.message)