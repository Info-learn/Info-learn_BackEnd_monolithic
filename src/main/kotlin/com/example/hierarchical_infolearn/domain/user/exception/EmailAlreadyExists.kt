package com.example.hierarchical_infolearn.domain.user.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class EmailAlreadyExists(data: String):GlobalError(ErrorCode.EMAIL_ALREADY_EXISTS, data) {
}