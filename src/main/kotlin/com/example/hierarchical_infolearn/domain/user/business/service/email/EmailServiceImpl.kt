package com.example.hierarchical_infolearn.domain.user.business.service.email

import com.example.hierarchical_infolearn.domain.user.data.entity.common.token.CodeType
import com.example.hierarchical_infolearn.domain.user.data.entity.token.code.AuthCode
import com.example.hierarchical_infolearn.domain.user.data.repo.token.code.CodeRepository
import com.example.hierarchical_infolearn.domain.user.data.repo.user.UserRepository
import com.example.hierarchical_infolearn.domain.user.exception.EmailAlreadyExists
import com.example.hierarchical_infolearn.infra.mail.MailUtil
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl(
    private val mailUtil: MailUtil,
    private val codeRepository: CodeRepository,
    private val userRepository: UserRepository,
):EmailService {

    companion object {
        const val CODE_LENGTH = 6
        const val AUTH_MAIL_TITLE = "[INFOLEARN 인증번호]"
    }

    override fun sendCodeToEmail(email: String) {
        if(userRepository.existsByEmail(email)) throw EmailAlreadyExists(email)
        val random = RandomStringUtils.randomNumeric(CODE_LENGTH)
        println("number : $random")
        codeRepository.save(
            AuthCode(
                email = email,
                code = random,
                type = CodeType.SIGNUP
            )
        )
        val map = HashMap<String, List<String>>()

        val split = random.split("").toMutableList()
        split.removeAt(0)
        split.removeAt(split.size - 1)
        map["code"] = split
        mailUtil.sendHtmlMail(email, AUTH_MAIL_TITLE, "mail.html", map)
    }


}