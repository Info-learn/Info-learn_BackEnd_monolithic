package com.example.hierarchical_infolearn.domain.til.presention.controller

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.annotation.OnConnect
import com.corundumstudio.socketio.annotation.OnDisconnect
import com.corundumstudio.socketio.annotation.OnEvent
import com.example.hierarchical_infolearn.domain.til.business.dto.request.JoinSocketTIlRequest
import com.example.hierarchical_infolearn.domain.til.business.dto.request.UpDateTilRequest
import com.example.hierarchical_infolearn.domain.til.business.service.socket.TilSocketService
import com.example.hierarchical_infolearn.global.config.websocket.property.SocketProperties
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import java.util.concurrent.CompletableFuture
import javax.validation.Valid

@Validated
@Component
class TilSocketHandler(
    private val server: SocketIOServer,
    private val service: TilSocketService,
){

    private val log = LoggerFactory.getLogger(this.javaClass)

    @OnConnect
    fun onConnect(socketIOClient: SocketIOClient) {
        log.info(socketIOClient.sessionId.toString() + " connected")
    }

    @OnDisconnect
    fun onDisconnect(socketIOClient: SocketIOClient) {
        log.info(socketIOClient.sessionId.toString() + " disconnected")
    }

    @OnEvent(value = SocketProperties.TIL)
    fun updateTil(
        client: SocketIOClient,
        ackRequest: AckRequest,
        @Valid @RequestBody
        request: UpDateTilRequest
    ) {
        service.modifyTil(server, client, request)

        ackRequest.sendAckData( CompletableFuture.supplyAsync { "til updated" } )
    }

    @OnEvent(value = SocketProperties.JOIN)
    fun joinTil(
        client: SocketIOClient,
        ackRequest: AckRequest,
        @RequestBody
        request: JoinSocketTIlRequest
    ) {

        service.joinTIl(server, client, request.tilId!!)

        ackRequest.sendAckData( CompletableFuture.supplyAsync { "join success" } )
    }


}