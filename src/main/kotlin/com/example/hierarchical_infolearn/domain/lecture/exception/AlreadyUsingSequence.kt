package com.example.hierarchical_infolearn.domain.lecture.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class AlreadyUsingSequence(data: String): GlobalError(ErrorCode.ALREADY_USING_SEQUENCE, data)