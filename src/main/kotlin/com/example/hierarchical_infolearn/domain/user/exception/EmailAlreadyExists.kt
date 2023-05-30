package com.example.hierarchical_infolearn.domain.user.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalException

object EmailAlreadyExists:GlobalException(ErrorCode.EMAIL_ALREADY_EXISTS)