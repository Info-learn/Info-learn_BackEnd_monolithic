package com.example.hierarchical_infolearn.domain.lecture.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class IncorrectChapter(data:String): GlobalError(ErrorCode.INCORRECT_CHAPTER, data) {
}