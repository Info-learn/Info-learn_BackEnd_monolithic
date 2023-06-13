package com.example.hierarchical_infolearn.domain.til.presention.controller

import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.annotation.OnEvent
import com.example.hierarchical_infolearn.domain.til.business.dto.request.JoinSocketTIlRequest
import com.example.hierarchical_infolearn.domain.til.business.dto.request.UpDateTilRequest
import com.example.hierarchical_infolearn.domain.til.business.service.socket.UpDateTilService
import com.example.hierarchical_infolearn.domain.til.business.service.socket.JoinSocketTilService
import com.example.hierarchical_infolearn.global.config.websocket.property.SocketProperties
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class TilSocketController(
    private val socketIOServer: SocketIOServer,
    private val joinSocketTilService: JoinSocketTilService,
    private val upDateTilService: UpDateTilService
) {

    @OnEvent(SocketProperties.TIL)
    fun updateTil(
        socketIOClient: SocketIOClient,
        @Valid @RequestBody
        request: UpDateTilRequest
    ) {
        upDateTilService.execute(socketIOServer, socketIOClient, request)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @OnEvent(SocketProperties.JOIN)
    fun enterTil(
        socketIOClient: SocketIOClient,
        @Valid @RequestBody
        request: JoinSocketTIlRequest
    ) {
        joinSocketTilService.execute(socketIOClient, request)
    }
}