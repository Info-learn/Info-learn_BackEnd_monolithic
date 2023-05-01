package com.example.hierarchical_infolearn.domain.user.business.service.auth

import com.example.hierarchical_infolearn.domain.user.business.dto.request.SignInRequest
import com.example.hierarchical_infolearn.domain.user.business.dto.request.StudentSignUpRequest
import com.example.hierarchical_infolearn.domain.user.business.dto.request.TeacherSignUpRequest
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse
import com.example.hierarchical_infolearn.global.security.jwt.data.TokenResponse

interface AuthService {

    fun checkAccountIdDuplicate(accountId: String)

    fun checkAuthCode(email: String, authCode: String)

    fun studentSignUp(req: StudentSignUpRequest): PreSignedUrlResponse?

    fun signIn(req: SignInRequest): TokenResponse

    fun teacherSignUp(req: TeacherSignUpRequest): PreSignedUrlResponse?
}