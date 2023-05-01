package com.example.hierarchical_infolearn.domain.lecture.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class LectureTagNotFound(data: String): GlobalError(ErrorCode.LECTURE_TAG_NOT_FOUND, data) {
}