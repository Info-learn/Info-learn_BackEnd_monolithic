package com.example.hierarchical_infolearn.domain.til.data.entity.tag

import com.example.hierarchical_infolearn.domain.til.data.entity.Til
import com.example.hierarchical_infolearn.global.base.entity.BaseTimeEntity
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity(name = "til_tag_usage")
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["tag_id","til_id"])])
@IdClass(TagUsage.IdClass::class)
class TagUsage(
    tag: Tag,
    til: Til
): BaseTimeEntity(), Persistable<TagUsage.IdClass> {

    @Id @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false, columnDefinition = "varchar(20) binary")
    val tag: Tag = tag

    @Id @ManyToOne
    @JoinColumn(name = "til_id", nullable = false, columnDefinition = "BINARY(16)")
    val til: Til = til

    data class IdClass (
        val tag: String? = null,
        val til: UUID? = null,
    ): Serializable

    override fun getId() = IdClass(this.tag.id, this.til.id)

    override fun isNew() = this.createdAt == null
}