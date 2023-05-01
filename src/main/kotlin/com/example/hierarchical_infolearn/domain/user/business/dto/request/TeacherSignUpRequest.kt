package com.example.hierarchical_infolearn.domain.user.business.dto.request

import com.example.hierarchical_infolearn.global.file.dto.FileRequest
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class TeacherSignUpRequest(
    @field:NotBlank(message = "공백이거나 NULL 값일 수 없습니다")
    val accountId: String,
    @field:Pattern(regexp = "^(?=.*?[A-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,30}\$", message = "비밀번호는 영문자,숫자,특수문자 8~30자이여야 합니다")
    val password: String,
    @field:Pattern(regexp = "[a-zA-Z\\d+_.]+@dsm.hs.kr\$", message = "올바른 도메인이 아닙니다")
    val email: String,
    @field:Size(min = 6, max = 6, message = "인증번호는 6자리 입니다")
    val authCode: String,
    val nickname: String?,
    @field:Size(min = 6, max = 6, message = "교사 코드는 6자리 입니다")
    val teacherCode: String,
    @field:Valid
    val profileImage: FileRequest?
)
