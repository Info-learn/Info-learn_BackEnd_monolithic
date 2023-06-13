package com.example.hierarchical_infolearn.domain.til.business.service.socket

import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.example.hierarchical_infolearn.domain.til.business.dto.request.UpDateTilRequest
import com.example.hierarchical_infolearn.domain.til.business.dto.response.TIlResponse
import com.example.hierarchical_infolearn.domain.til.data.entity.Til
import com.example.hierarchical_infolearn.domain.til.data.entity.socket.TilUser
import com.example.hierarchical_infolearn.domain.til.data.repo.TilRepository
import com.example.hierarchical_infolearn.domain.til.data.repo.TilUserRepository
import com.example.hierarchical_infolearn.domain.til.exception.TilNotFound
import com.example.hierarchical_infolearn.domain.til.exception.TilUserNotFound
import com.example.hierarchical_infolearn.global.config.websocket.property.ClientProperties
import com.example.hierarchical_infolearn.global.config.websocket.property.SocketProperties
import com.example.hierarchical_infolearn.global.utils.CurrentUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class UpDateTilServiceImpl(
    private val currentUtil: CurrentUtil,
    private val tilRepository: TilRepository,
    private val tilUserRepository: TilUserRepository
): UpDateTilService {

    override fun execute(socketIOServer: SocketIOServer, socketIOClient: SocketIOClient, request: UpDateTilRequest) {

        val user = currentUtil.getCurrentUser(socketIOClient)

        val til = tilRepository.findByIdOrNull(getTilRoomId(socketIOClient))
            ?: throw TilNotFound

        val tilUser = tilUserRepository.findByIdOrNull(TilUser.IdClass(user.accountId, til.id))
            ?: throw TilUserNotFound

        val newTil = tilRepository.save(Til(
            request.id,
            request.title,
            request.searchTitle,
            request.subTitle,
            request.isPrivate!!,
            request.content!!,
            user
        ))

        socketIOServer
            .getRoomOperations(newTil.id.toString())
            .clients
            .forEach {
                it.sendEvent(SocketProperties.TIL, TIlResponse.of(til, tilUser, user))
            }
    }

    private fun getTilRoomId(socketIOClient: SocketIOClient): UUID {

        if (!socketIOClient.has(ClientProperties.TIL_KEY)) throw TilNotFound

        return UUID.fromString(socketIOClient.get(ClientProperties.TIL_KEY))
    }
}
