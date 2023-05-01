package com.example.hierarchical_infolearn.global.error.common

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class InvalidParameterException(data: String): GlobalError(ErrorCode.INVALID_PARAMETER, data)