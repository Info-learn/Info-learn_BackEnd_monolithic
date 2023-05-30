package com.example.hierarchical_infolearn.global.config.security.jwt.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalException

object ExpiredTokenException: GlobalException(ErrorCode.EXPIRED_TOKEN)