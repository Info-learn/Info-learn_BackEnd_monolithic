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
import com.example.hierarchical_infolearn.domain.til.exception.TillKeyNotFound
import com.example.hierarchical_infolearn.global.config.websocket.property.SocketProperties
import com.example.hierarchical_infolearn.global.utils.CurrentUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class TilSocketServiceImpl(
    private val currentUtil: CurrentUtil,
    private val tilRepository: TilRepository,
    private val tilUserRepository: TilUserRepository
): TilSocketService {

    @Transactional
    override fun modifyTil(server: SocketIOServer, client: SocketIOClient, request: UpDateTilRequest){

        val user = currentUtil.getCurrentUser(client)

        val tilId = client.get<UUID>("til")
            ?: throw TillKeyNotFound

        if (!tilRepository.existsById(tilId)) {
            throw TilNotFound
        }

        val tilUser = tilUserRepository.findByIdOrNull(TilUser.IdClass(user.accountId, tilId))
            ?: throw TilNotFound // tilUser가 없으면 잘못된 유저라는 뜻 -> til이 존재한다는걸 알려주지 않기 403이 아닌 404바인드

        val til = tilRepository.saveAndFlush(
            Til(
                tilId,
                request.title!!,
                request.searchTitle!!,
                request.subTitle,
                request.isPrivate!!,
                request.content!!,
                user
            )
        )

        server.getRoomOperations(tilId.toString())
            .sendEvent(SocketProperties.TIL, TIlResponse.of(til, tilUser, user))
    }

    override fun joinTIl(server: SocketIOServer, client: SocketIOClient, tilId: UUID) {

        val user = currentUtil.getCurrentUser(client)

        if (tilUserRepository.existsById(TilUser.IdClass(user.accountId, tilId))) {
            client.let {
                it.joinRoom(tilId.toString())
                it.set("til", tilId)
            }

            server.getRoomOperations(tilId.toString()).sendEvent(SocketProperties.JOIN, "${user.nickname} is joined")
        } else throw TilNotFound
    }
}
