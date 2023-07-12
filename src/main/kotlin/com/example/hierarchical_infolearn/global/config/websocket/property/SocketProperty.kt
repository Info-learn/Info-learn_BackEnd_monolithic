package com.example.hierarchical_infolearn.global.config.websocket.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "socket")
data class SocketProperty(
    val port: Int,
    val host: String
)