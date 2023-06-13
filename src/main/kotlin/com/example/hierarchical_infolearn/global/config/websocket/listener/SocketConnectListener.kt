package com.example.hierarchical_infolearn.global.config.websocket.listener

import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.annotation.OnConnect
import com.example.hierarchical_infolearn.domain.user.data.entity.User
import com.example.hierarchical_infolearn.global.utils.CurrentUtil
import com.example.hierarchical_infolearn.global.utils.SocketTilUtils
import org.springframework.stereotype.Component


@Component
class SocketConnectListener(
    private val currentUtil: CurrentUtil,
    private val socketTilUtils: SocketTilUtils
){

    companion object{
        const val USER_KEY = "user_key"
    }

    @OnConnect
    fun onConnect(socketIOClient: SocketIOClient) {

        val user: User = currentUtil.getCurrentUser(socketIOClient)

        socketIOClient.set(USER_KEY, user.accountId)

        socketTilUtils.joinAllTil(socketIOClient, user)
    }

}