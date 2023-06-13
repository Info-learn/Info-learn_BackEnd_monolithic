package com.example.hierarchical_infolearn.domain.user.data.entity.token.refreshToken

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash
class RefreshToken(
    id: String,
    token: String,
) {
    @Id
    var id = id

    var token = token
        protected set

    @TimeToLive
    var ttl: Long = 604800
        protected set

    fun reset(token: String): RefreshToken {
        this.token = token
        this.ttl = 604800
        return this
    }
}