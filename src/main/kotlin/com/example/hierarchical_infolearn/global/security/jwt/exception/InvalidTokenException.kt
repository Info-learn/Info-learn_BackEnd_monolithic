package com.example.hierarchical_infolearn.global.security.jwt.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class InvalidTokenException(data: String): GlobalError(ErrorCode.INVALID_TOKEN, data)