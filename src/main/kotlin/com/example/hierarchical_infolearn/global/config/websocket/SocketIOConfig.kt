package com.example.hierarchical_infolearn.global.config.websocket

import com.corundumstudio.socketio.SocketConfig
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner
import com.corundumstudio.socketio.protocol.JacksonJsonSupport
import com.example.hierarchical_infolearn.global.config.websocket.property.SocketProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SocketIOConfig(
    private val property: SocketProperty
) {

    @Bean
    fun socketIOServer(): SocketIOServer {

        val socketConfig = SocketConfig()
        socketConfig.isReuseAddress = true

        val config = com.corundumstudio.socketio.Configuration()

        config.let {
            it.hostname = property.host
            it.port = property.port
            it.socketConfig = socketConfig
            it.jsonSupport = JacksonJsonSupport()
        }

        return SocketIOServer(config)
    }

    @Bean
    fun springAnnotationScanner(socketIOServer: SocketIOServer) = SpringAnnotationScanner(socketIOServer)

}