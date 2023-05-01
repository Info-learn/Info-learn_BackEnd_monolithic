package com.example.hierarchical_infolearn.global.error.common

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class NoAuthenticationException(data: String): GlobalError(ErrorCode.NO_AUTHENTICATION, data)