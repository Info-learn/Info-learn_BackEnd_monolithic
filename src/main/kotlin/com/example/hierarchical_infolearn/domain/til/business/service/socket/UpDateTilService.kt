package com.example.hierarchical_infolearn.domain.til.business.service.socket

import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.example.hierarchical_infolearn.domain.til.business.dto.request.UpDateTilRequest

interface UpDateTilService {

    fun execute(socketIOServer: SocketIOServer, socketIOClient: SocketIOClient, request: UpDateTilRequest)
}
