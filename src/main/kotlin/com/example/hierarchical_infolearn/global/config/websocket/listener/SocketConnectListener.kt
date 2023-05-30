package com.example.hierarchical_infolearn.global.config.websocket.listener

import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.annotation.OnConnect
import org.springframework.stereotype.Component


@Component
class SocketConnectListener(
){

    @OnConnect
    fun onConnect(socketIOClient: SocketIOClient) {
    }

}