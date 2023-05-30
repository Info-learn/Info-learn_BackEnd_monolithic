package com.example.hierarchical_infolearn.domain.lecture.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalException

object ChapterNotFoundException: GlobalException(ErrorCode.CHAPTER_NOT_FOUND)