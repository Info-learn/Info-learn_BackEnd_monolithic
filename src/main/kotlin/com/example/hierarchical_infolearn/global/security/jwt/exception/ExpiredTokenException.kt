package com.example.hierarchical_infolearn.global.security.jwt.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class ExpiredTokenException(data: String): GlobalError(ErrorCode.EXPIRED_TOKEN, data)