package com.example.hierarchical_infolearn.domain.user.business.service.auth

import com.example.hierarchical_infolearn.domain.user.business.dto.request.SignInRequest
import com.example.hierarchical_infolearn.domain.user.business.dto.request.StudentSignUpRequest
import com.example.hierarchical_infolearn.domain.user.business.dto.request.TeacherSignUpRequest
import com.example.hierarchical_infolearn.domain.user.data.entity.common.token.CodeType
import com.example.hierarchical_infolearn.domain.user.data.entity.common.user.Role
import com.example.hierarchical_infolearn.domain.user.data.entity.student.Student
import com.example.hierarchical_infolearn.domain.user.data.entity.teacher.Teacher
import com.example.hierarchical_infolearn.domain.user.data.entity.token.refreshToken.RefreshToken
import com.example.hierarchical_infolearn.domain.user.data.repo.token.code.CodeRepository
import com.example.hierarchical_infolearn.domain.user.data.repo.token.refreshToken.RefreshTokenRepository
import com.example.hierarchical_infolearn.domain.user.data.repo.user.StudentRepository
import com.example.hierarchical_infolearn.domain.user.data.repo.user.TeacherRepository
import com.example.hierarchical_infolearn.domain.user.data.repo.user.UserRepository
import com.example.hierarchical_infolearn.domain.user.exception.*
import com.example.hierarchical_infolearn.global.file.dto.PreSignedUrlResponse
import com.example.hierarchical_infolearn.global.config.security.jwt.TokenProvider
import com.example.hierarchical_infolearn.global.config.security.jwt.data.TokenResponse
import com.example.hierarchical_infolearn.infra.s3.S3Util
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val studentRepository: StudentRepository,
    private val teacherRepository: TeacherRepository,
    private val codeRepository: CodeRepository,
    private val passwordEncoder: PasswordEncoder,
    private val s3Util: S3Util,
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
):AuthService {

    override fun checkAccountIdDuplicate(accountId: String) {
        checkAccountId(accountId)
    }

    override fun checkAuthCode(email: String, authCode: String) {
        val checkEmail = codeRepository.findByIdOrNull(email)?: throw EmailNotFound
        if(checkEmail.code != authCode) throw IncorrectAuthCode
    }

    override fun studentSignUp(req: StudentSignUpRequest): PreSignedUrlResponse? {
        checkAccountId(req.accountId)
        checkAuthCodeAndDelete(req.email, req.authCode)

        val encPw = passwordEncoder.encode(req.password)

        val student = Student(
            accountId = req.accountId,
            nickname = req.nickname ?: req.accountId,
            email = req.email,
            password = encPw
        )
        val studentEntity = studentRepository.save(student)

        req.profileImage?.let {

            val (preSignedUrl, file) = preSignedUrl(Role.STUDENT, it.fileName, it.contentType, it.fileSize ,req.accountId)

            studentEntity.uploadProfileImage(
                file
            )
            return@studentSignUp preSignedUrl
        }
        return null
    }

    override fun teacherSignUp(req: TeacherSignUpRequest): PreSignedUrlResponse? {
        checkAccountId(req.accountId)
        checkAuthCodeAndDelete(req.email, req.authCode)
        checkTeacherCode(req.teacherCode)

        val encPw = passwordEncoder.encode(req.password)

        val teacher = Teacher(
            accountId = req.accountId,
            nickname =req.nickname?: req.accountId,
            email = req.email,
            password = encPw
        )

        val teacherEntity = teacherRepository.save(teacher)

        req.profileImage?.let {

            val (preSignedUrl, file) = preSignedUrl(Role.TEACHER, it.fileName, it.contentType, it.fileSize,req.accountId)

            teacherEntity.uploadProfileImage(
               file
            )
            return@teacherSignUp preSignedUrl
        }
        return null
    }

    private fun preSignedUrl(type: Role, fileName: String, contentType: String, fileSize: Long,accountId: String): Pair<PreSignedUrlResponse, String>{

        val file = s3Util.getPreSignedUrl(
            fileName,
            contentType,
            fileSize,
            "${type.name}/${accountId}",
            "PROFILE_IMAGE"
        )

        val preSignedUrl = PreSignedUrlResponse(
            file.fileUrl,
            file.fileName
        )

        file.removeParameter()

        return Pair(preSignedUrl, file.fileUrl)
    }

    private fun checkTeacherCode(teacherCode: String) {
        if(teacherCode != "123456") throw IncorrectTeacherCode
    }

    private fun checkAuthCodeAndDelete(email: String, authCode: String) {
        val checkEmail = codeRepository.findByIdOrNull(email)?: throw EmailNotFound
        if(checkEmail.code != authCode || checkEmail.type != CodeType.SIGNUP) throw IncorrectAuthCode
        codeRepository.delete(checkEmail)
    }

    private fun checkAccountId(accountId: String) {
        val isDuplicate = userRepository.existsByAccountId(accountId)
        if(isDuplicate) throw AccountIdAlreadyExists
    }

    override fun signIn(req: SignInRequest): TokenResponse {
        val user = userRepository.findByIdOrNull(req.accountId) ?: throw UserNotFoundException

        if(!passwordEncoder.matches(req.password, user.password)) throw IncorrectPassword
        val tokenUUID = UUID.randomUUID().toString()
        val response = tokenProvider.encode(user.accountId, user.role.name, tokenUUID)
        refreshTokenRepository.findByIdOrNull(user.accountId)?.reset(response.refreshToken)
            ?: RefreshToken(
                user.accountId,
                response.refreshToken
        )
        return response
    }

}