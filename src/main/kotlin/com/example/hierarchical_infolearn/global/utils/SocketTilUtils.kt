package com.example.hierarchical_infolearn.global.utils

import com.corundumstudio.socketio.SocketIOClient
import com.example.hierarchical_infolearn.domain.til.data.entity.socket.TilUser
import com.example.hierarchical_infolearn.domain.til.data.repo.TilRepository
import com.example.hierarchical_infolearn.domain.til.data.repo.TilUserRepository
import com.example.hierarchical_infolearn.domain.til.exception.TilNotFound
import com.example.hierarchical_infolearn.domain.til.exception.TilUserNotFound
import com.example.hierarchical_infolearn.domain.user.data.entity.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
@Transactional(readOnly = true)
class SocketTilUtils(
    private val tilRepository: TilRepository,
    private val tilUserRepository: TilUserRepository
) {

    companion object {
        private const val TIL_KEY = "til_key"
    }

    fun joinAllTil(socketIOClient: SocketIOClient, user: User) {

        val tilUserList = tilUserRepository.findAllByUser(user)

        if (tilUserList.isNullOrEmpty()) throw TilUserNotFound

        tilUserList
            .map { socketIOClient.joinRoom(it.til.id.toString()) }
            .toList()
    }

    fun joinOneTil(socketIOClient: SocketIOClient, user: User, tilId: UUID) {

        if(tilRepository.existsById(tilId)) throw TilNotFound

        tilUserRepository.findByIdOrNull(TilUser.IdClass(user.accountId, tilId)) ?: throw TilUserNotFound

        socketIOClient.let {
            it.allRooms.forEach { room ->  socketIOClient.leaveRoom((room)) }
            it[TIL_KEY] = tilId
            it.joinRoom(tilId.toString())
        }
    }
}