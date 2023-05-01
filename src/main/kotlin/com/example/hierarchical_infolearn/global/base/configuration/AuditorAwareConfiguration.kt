package com.example.hierarchical_infolearn.global.base.configuration

import com.example.hierarchical_infolearn.domain.user.data.entity.common.user.Role
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJpaAuditing
class AuditorAwareConfiguration: AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> {
        val authentication = SecurityContextHolder.getContext().authentication?: return Optional.empty()
        if(!authentication.authorities.any{ it.toString() == Role.STUDENT.name || it.toString() == Role.TEACHER.name}) return Optional.empty()
        return Optional.of(authentication.name)
    }
}