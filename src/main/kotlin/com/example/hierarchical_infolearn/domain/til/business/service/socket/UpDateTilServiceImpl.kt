package com.example.hierarchical_infolearn.domain.til.business.service.socket

import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.example.hierarchical_infolearn.domain.til.business.dto.request.UpDateTilRequest
import com.example.hierarchical_infolearn.domain.til.data.entity.Til
import com.example.hierarchical_infolearn.domain.til.data.entity.socket.TilUser
import com.example.hierarchical_infolearn.domain.til.data.repo.TilRepository
import com.example.hierarchical_infolearn.domain.til.data.repo.TilUserRepository
import com.example.hierarchical_infolearn.domain.til.exception.TilNotFound
import com.example.hierarchical_infolearn.domain.til.exception.TilUserNotFound
import com.example.hierarchical_infolearn.global.utils.CurrentUtil
import com.example.hierarchical_infolearn.global.utils.SocketTilUtils
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

    companion object {
        private const val TIL_KEY = "til_key"

        private const val TIL = "til"
    }

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
            user!!
        ))

//        socketIOServer
//            .getRoomOperations(newTil.id.toString())
//            .clients
//            .forEach {
//
//                it.sendEvent(TIL, ChatResponse.of(TIL, it === socketIOClient))
//
//                val clientTilUser = tilUserRepository.findByIdOrNull(
//                    TilUser.IdClass(currentUtil.getCurrentUser(it).accountId, newTil.id!!))
//
//                clientTilUser.updateLastReadTime()
//            }
    }

    private fun getTilRoomId(socketIOClient: SocketIOClient): UUID {
        if (!socketIOClient.has(TIL_KEY)) {
            throw TilNotFound
        }
        return UUID.fromString(socketIOClient.get(TIL_KEY))
    }
}
