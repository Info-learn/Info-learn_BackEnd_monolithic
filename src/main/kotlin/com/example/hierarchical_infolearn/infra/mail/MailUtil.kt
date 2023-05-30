package com.example.hierarchical_infolearn.infra.mail

import com.example.hierarchical_infolearn.infra.mail.env.MailProperty
import com.example.hierarchical_infolearn.infra.mail.execption.MailSendingException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import javax.mail.MessagingException
import javax.mail.internet.MimeMessage

@Service
class MailUtil (
    private val jms: JavaMailSender,
    private val templateEngine: TemplateEngine,
    private val mailProperty: MailProperty
) {

    fun sendHtmlMail(to: String, title: String, templatePath: String, models: Map<String, Any>) {
        val message: MimeMessage = jms.createMimeMessage()
        val context = Context()
        models.forEach(context::setVariable)
        val content: String = templateEngine.process(templatePath, context)

        try {
            val helper = MimeMessageHelper(message, true, "UTF-8")
            helper.let {
                it.setFrom(mailProperty.username)
                it.setSubject(title)
                it.setTo(to)
                it.setText(content, true)
            }
        } catch (e: MessagingException) {
            throw MailSendingException
        }
        jms.send(message)
    }




}