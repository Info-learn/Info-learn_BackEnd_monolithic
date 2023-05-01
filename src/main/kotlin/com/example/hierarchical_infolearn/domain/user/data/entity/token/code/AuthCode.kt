package com.example.hierarchical_infolearn.domain.user.data.entity.token.code

import com.example.hierarchical_infolearn.domain.user.data.entity.common.token.CodeType
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash
class AuthCode(
    email: String,
    code: String,
    type: CodeType,
) {
    @Id
    val email: String = email

    val code: String = code

    val type: CodeType = type

    @TimeToLive
    var ttl: Long = 300
}