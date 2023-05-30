package com.example.hierarchical_infolearn.infra.mail.execption

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalException

object MailSendingException: GlobalException(ErrorCode.EMAIL_ERROR)