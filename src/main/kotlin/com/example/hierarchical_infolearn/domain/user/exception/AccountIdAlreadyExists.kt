package com.example.hierarchical_infolearn.domain.user.exception

import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.GlobalError


class AccountIdAlreadyExists(data: String): GlobalError(ErrorCode.ACCOUNT_ID_ALREADY_EXISTS, data)