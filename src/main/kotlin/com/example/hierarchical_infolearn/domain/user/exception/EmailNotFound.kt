package com.example.hierarchical_infolearn.domain.user.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class EmailNotFound(data: String): GlobalError(ErrorCode.EMAIL_NOT_FOUND, data) {
}