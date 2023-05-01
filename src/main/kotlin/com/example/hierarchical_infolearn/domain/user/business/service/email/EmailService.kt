package com.example.hierarchical_infolearn.domain.user.business.service.email

interface EmailService {

    fun sendCodeToEmail(email: String)
}