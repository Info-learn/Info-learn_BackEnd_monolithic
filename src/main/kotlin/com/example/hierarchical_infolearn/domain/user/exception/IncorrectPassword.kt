package com.example.hierarchical_infolearn.domain.user.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalException

object IncorrectPassword: GlobalException(ErrorCode.INCORRECT_PASSWORD)