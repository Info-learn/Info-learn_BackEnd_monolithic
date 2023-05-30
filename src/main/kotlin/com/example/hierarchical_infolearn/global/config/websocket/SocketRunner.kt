package com.example.hierarchical_infolearn.global.config.websocket

import com.corundumstudio.socketio.SocketIOServer
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class SocketRunner(
    private val socketIOServer: SocketIOServer
): CommandLineRunner {

    override fun run(vararg args: String) {
        socketIOServer.start()
    }
}