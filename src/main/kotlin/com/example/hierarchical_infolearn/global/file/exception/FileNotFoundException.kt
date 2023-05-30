package com.example.hierarchical_infolearn.global.file.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalException

object FileNotFoundException: GlobalException(ErrorCode.FILE_NOT_FOUND)