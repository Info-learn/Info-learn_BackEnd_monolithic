package com.example.hierarchical_infolearn.domain.til.business.service.socket

import com.corundumstudio.socketio.SocketIOClient
import com.example.hierarchical_infolearn.domain.til.business.dto.request.JoinSocketTIlRequest
import com.example.hierarchical_infolearn.domain.user.data.entity.User
import com.example.hierarchical_infolearn.global.utils.CurrentUtil
import com.example.hierarchical_infolearn.global.utils.SocketTilUtils
import org.springframework.stereotype.Service

@Service
class JoinSocketTilServiceImpl(
    private val currentUtil: CurrentUtil,
    private val socketTilUtils: SocketTilUtils
): JoinSocketTilService {

    override fun execute(socketIOClient: SocketIOClient, request: JoinSocketTIlRequest) {

        val user: User = currentUtil.getCurrentUser(socketIOClient)

        if (request.isJoinRoom) {
            socketTilUtils.joinOneTil(socketIOClient, user, request.tilId)
        } else {
            socketTilUtils.joinAllTil(socketIOClient, user)
        }
    }
}