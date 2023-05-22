package com.example.hierarchical_infolearn.domain.lecture.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class DuplicationSequenceException(data: String):GlobalError(ErrorCode.DUPLICATION_SEQUENCE, data)