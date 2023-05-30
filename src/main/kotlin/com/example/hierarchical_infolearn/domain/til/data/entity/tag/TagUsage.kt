package com.example.hierarchical_infolearn.domain.til.data.entity.tag

import com.example.hierarchical_infolearn.domain.til.data.entity.Til
import com.example.hierarchical_infolearn.global.base.entity.BaseTimeEntity
import org.springframework.data.domain.Persistable
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "tbl_til_tag_usage",uniqueConstraints = [UniqueConstraint(columnNames = ["tag_id","til_id"])])
@IdClass(TagUsageIdClass::class)
class TagUsage(
    tag: Tag,
    til: Til
): BaseTimeEntity(), Persistable<String>, Serializable {

    @Id @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false, columnDefinition = "varchar(20) binary")
    val tag: Tag = tag

    @Id @ManyToOne
    @JoinColumn(name = "til_id", nullable = false, columnDefinition = "BINARY(16)")
    val til: Til = til

    override fun getId(): String? {
        return this.id
    }

    override fun isNew(): Boolean {
        return this.createdAt == null
    }
}