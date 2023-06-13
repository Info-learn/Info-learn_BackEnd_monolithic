package com.example.hierarchical_infolearn.domain.til.data.repo

import com.example.hierarchical_infolearn.domain.til.data.entity.socket.TilUser
import com.example.hierarchical_infolearn.domain.user.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TilUserRepository: JpaRepository<TilUser, TilUser.IdClass> {
    abstract fun findAllByUser(user: User): MutableList<TilUser>?
}