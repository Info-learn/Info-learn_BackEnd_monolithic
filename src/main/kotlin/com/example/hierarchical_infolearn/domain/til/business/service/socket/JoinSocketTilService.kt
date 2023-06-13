package com.example.hierarchical_infolearn.domain.til.business.service.socket

import com.corundumstudio.socketio.SocketIOClient
import com.example.hierarchical_infolearn.domain.til.business.dto.request.JoinSocketTIlRequest

interface JoinSocketTilService {

    fun execute(socketIOClient: SocketIOClient, request: JoinSocketTIlRequest)
}