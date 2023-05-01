package com.example.hierarchical_infolearn.infra.mail.execption

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError

class MailSendingException(data: String): GlobalError(ErrorCode.EMAIL_ERROR, data)