package com.example.hierarchical_infolearn.domain.til.data.entity.socket

import com.example.hierarchical_infolearn.domain.til.data.entity.Til
import com.example.hierarchical_infolearn.domain.user.data.entity.User
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.util.*
import javax.persistence.*

@IdClass(TilUser.IdClass::class)
@Entity(name = "til_user")
@NamedEntityGraph(name = "TilUser.user", attributeNodes = [NamedAttributeNode("user")])
class TilUser(
    user: User,
    til: Til,
    role: Role = Role.OWNER
): Persistable<TilUser.IdClass> {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", nullable = false, columnDefinition = "VARCHAR(50)")
    var user: User = user
        protected set

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "til", nullable = false, columnDefinition = "BINARY(16)")
    var til: Til = til
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition = "CHAR(11)")
    var role: Role = role
        protected set

    data class IdClass(
        var user: String? = null,
        var til: UUID? = null
    ) : Serializable

    override fun getId() = IdClass(user.accountId, til.id)

    override fun isNew() = true
}