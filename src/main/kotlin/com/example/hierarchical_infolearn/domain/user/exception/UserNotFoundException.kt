package com.example.hierarchical_infolearn.domain.user.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class UserNotFoundException(data: String): GlobalError(ErrorCode.USER_NOT_FOUND, data)