package com.example.hierarchical_infolearn.domain.lecture.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class LectureNotFoundException(data: String): GlobalError(ErrorCode.LECTURE_NOT_FOUND, data) {
}