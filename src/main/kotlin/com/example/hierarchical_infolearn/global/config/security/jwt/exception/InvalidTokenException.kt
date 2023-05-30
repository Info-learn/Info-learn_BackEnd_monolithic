package com.example.hierarchical_infolearn.global.config.security.jwt.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalException

object InvalidTokenException: GlobalException(ErrorCode.INVALID_TOKEN)