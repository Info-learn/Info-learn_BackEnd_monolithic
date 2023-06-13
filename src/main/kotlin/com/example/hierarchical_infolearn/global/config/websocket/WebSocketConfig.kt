package com.example.hierarchical_infolearn.global.config.websocket

import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner
import com.example.hierarchical_infolearn.global.config.websocket.listener.SocketExceptionListener
import com.example.hierarchical_infolearn.global.config.websocket.property.SocketProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.corundumstudio.socketio.SocketConfig

@Configuration
class WebSocketConfig(
    private val property: SocketProperty
) {

    @Bean
    fun socketIOServer(): SocketIOServer {

        val socketConfig = SocketConfig()
        socketConfig.isReuseAddress = true

        val configuration = com.corundumstudio.socketio.Configuration()

        configuration.let {
            it.port = property.port
            it.origin = "*";
            it.socketConfig = socketConfig;
            it.exceptionListener = SocketExceptionListener()
        }

        return SocketIOServer(configuration);
    }

    @Bean
    fun springAnnotationScanner(socketIOServer: SocketIOServer) = SpringAnnotationScanner(socketIOServer)

}