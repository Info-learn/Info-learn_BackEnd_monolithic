package com.example.hierarchical_infolearn.domain.til.data.entity.socket

import com.example.hierarchical_infolearn.domain.til.data.entity.Til
import com.example.hierarchical_infolearn.domain.user.data.entity.User
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.util.*
import javax.persistence.*

@IdClass(TilUser.IdClass::class)
@Entity(name = "room_user")
class TilUser(
    user: User,
    til: Til,
): Persistable<TilUser.IdClass> {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    var user: User = user
        protected set

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room", nullable = false)
    var til: Til = til
        protected set

    data class IdClass(
        var user: String? = null,
        var til: UUID? = null
    ) : Serializable

    override fun getId() = IdClass(user.accountId, til.id)

    override fun isNew() = true
}