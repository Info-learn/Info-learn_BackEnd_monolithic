package com.example.hierarchical_infolearn.domain.user.presention.controller.auth

import com.example.hierarchical_infolearn.domain.user.business.dto.request.SignInRequest
import com.example.hierarchical_infolearn.domain.user.business.dto.request.StudentSignUpRequest
import com.example.hierarchical_infolearn.domain.user.business.dto.request.TeacherSignUpRequest
import com.example.hierarchical_infolearn.domain.user.business.service.auth.AuthService
import com.example.hierarchical_infolearn.domain.user.business.service.email.EmailService
import com.example.hierarchical_infolearn.global.error.data.ErrorResponse
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse
import com.example.hierarchical_infolearn.global.config.security.jwt.data.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/api/infolearn/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val emailService: EmailService,
) {


    @GetMapping("/check/account")
    @Operation(
        summary = "account_id 중복 검사",
        description = "해당 account_id가 중복인지 검사합니다",
        responses = [
            ApiResponse(responseCode = "200", description = "account_id가 중복이 아님"),
            ApiResponse(responseCode = "400", description = "account_id가 중복임", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ]
    )
    fun checkDuplicate(
        @RequestParam(required = true) accountId: String,
    ) {
        authService.checkAccountIdDuplicate(accountId)
    }

    @PostMapping("/email")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "이메일 인증번호 전송", description = "이메일에 인증번호를 전송합니다",
        responses = [
            ApiResponse(responseCode = "201", description = "이메일로 인증번호가 정상적으로 전송됨"),
            ApiResponse(responseCode = "400", description = "1. 이메일이 이미 존재함 \t\n 2. 올바른 도메인이 아님", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "502", description = "SMTP로 이메일 전송중 에러 발생", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ]
        )
    fun sendEmail(
        @Valid
        @RequestParam(required = true)
        @Pattern(regexp = "[a-zA-Z\\d+_.]+@dsm.hs.kr$", message = "올바른 도메인이 아닙니다.")
        email: String,
    ) {
        emailService.sendCodeToEmail(email = email)
    }

    @GetMapping("/check/code")
    @Operation(summary = "이메일 인증번호 확인", description = "해당 이메일과 인증번호가 일치하는지 확인합니다",
        responses = [
            ApiResponse(responseCode = "200", description = "이메일과 인증번호가 일치함"),
            ApiResponse(responseCode = "400", description = "인증코드가 일치하지 않음", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "이메일을 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ]
        )
    fun checkAuthCode(
        @RequestParam(required = true) email: String,
        @RequestParam(required = true) code: String,
    ) {
        authService.checkAuthCode(
            email = email,
            authCode = code,
        )
    }

    @PostMapping("/sign-up/student")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "학생 회원가입", description = "학생이 학교 이메일에 전송된 인증번호로 인증한 후 프로필 사진, 아이디, 닉네임, 비밀번호를 기입한 후 회원가입합니다.",
        responses = [
            ApiResponse(responseCode = "201", description = "정상적으로 회원가입이 성공함", content = [Content(schema = Schema(implementation = PreSignedUrlResponse::class))]),
            ApiResponse(responseCode = "400", description = "1. 인증코드가 일치하지 않음 \n 2. account_id가 이미 존재함 \n 3. 공백이거나 NULL 값일 수 없음 \n 4. 비밀번호는 영문자,숫자,특수문자 8~30자이여야함 \n 5. 올바른 도메인이 아님 \n 6. 인증번호는 6자리임", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "이메일을 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ]
    )
    fun studentSignUp(
        @Valid
        @RequestBody(required = true) request: StudentSignUpRequest,
    ): PreSignedUrlResponse? {
        return authService.studentSignUp(
            req = request,
        )
    }
    @PostMapping("/sign-up/teacher")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "교사 회원가입", description = "교사가 학교 이메일에 전송된 인증번호로 인증한 후 프로필 사진, 아이디, 닉네임, 비밀번호, 교사 인증코드를 기입한 후 회원가입합니다.",
        responses = [
            ApiResponse(responseCode = "201", description = "정상적으로 회원가입이 성공함", content = [Content(schema = Schema(implementation = PreSignedUrlResponse::class))]),
            ApiResponse(responseCode = "400", description = "1. 인증코드가 일치하지 않음 \n 2. account_id가 이미 존재함 \n 3. 공백이거나 NULL 값일 수 없음 \n 4. 비밀번호는 영문자,숫자,특수문자 8~30자이여야함 \n 5. 올바른 도메인이 아님 \n 6. 인증번호는 6자리임 \n 7. 교사 코드는 6자리 입니다", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "이메일을 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ]
        )
    fun teacherSignUp(
        @Valid
        @RequestBody(required = true) request: TeacherSignUpRequest,
    ): PreSignedUrlResponse? {
        return authService.teacherSignUp(
            req = request,
        )
    }
    
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "로그인", description = "account_id 와 password를 기입한 후 로그인하여 토큰을 발급합니다",
        responses = [
            ApiResponse(responseCode = "201", description = "로그인 성공함", content = [Content(schema = Schema(implementation = TokenResponse::class))]),
            ApiResponse(responseCode = "400", description = "잘못된 비밀번호를 입력함", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
        ]
        )
    fun signIn(
        @RequestBody
        request: SignInRequest
    ): TokenResponse = authService.signIn(request)
}