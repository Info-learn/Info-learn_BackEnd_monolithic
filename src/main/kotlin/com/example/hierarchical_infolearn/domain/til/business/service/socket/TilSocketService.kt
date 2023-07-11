package com.example.hierarchical_infolearn.domain.til.business.service.socket

import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.example.hierarchical_infolearn.domain.til.business.dto.request.UpDateTilRequest
import java.util.*

interface TilSocketService {

    fun modifyTil(server: SocketIOServer, client: SocketIOClient, request: UpDateTilRequest)

    fun joinTIl(server: SocketIOServer, client: SocketIOClient, tilId: UUID)
}
