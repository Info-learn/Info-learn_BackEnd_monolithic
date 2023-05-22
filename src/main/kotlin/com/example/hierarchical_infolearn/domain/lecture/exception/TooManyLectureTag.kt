package com.example.hierarchical_infolearn.domain.lecture.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class TooManyLectureTag(data: String): GlobalError(ErrorCode.TOO_MANY_LECTURE_TAG, data) {
}