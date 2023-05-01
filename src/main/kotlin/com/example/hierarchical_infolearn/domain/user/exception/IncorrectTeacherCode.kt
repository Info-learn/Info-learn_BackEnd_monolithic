package com.example.hierarchical_infolearn.domain.user.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class IncorrectTeacherCode(data: String): GlobalError(ErrorCode.INCORRECT_TEACHER_CODE, data)