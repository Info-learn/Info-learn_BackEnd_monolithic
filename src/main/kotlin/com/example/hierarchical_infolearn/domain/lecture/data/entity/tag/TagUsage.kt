package com.example.hierarchical_infolearn.domain.lecture.data.entity.tag

import com.example.hierarchical_infolearn.domain.lecture.data.entity.Lecture
import com.example.hierarchical_infolearn.global.base.entity.BaseTimeEntity
import org.springframework.data.domain.Persistable
import java.io.Serializable
import javax.persistence.*

@Entity(name = "lecture_tag_usage")
@IdClass(TagUsage.IdClass::class)
class TagUsage(
    tag: Tag,
    lecture: Lecture
): BaseTimeEntity(), Persistable<TagUsage.IdClass>{

    @Id @ManyToOne
    @JoinColumn(name = "tag", nullable = false, columnDefinition = "varchar(20) binary")
    val tag: Tag = tag

    @Id
    @ManyToOne
    @JoinColumn(name = "lecture", nullable = false)
    val lecture: Lecture = lecture

    data class IdClass(
        val tag: String? = null,
        val lecture: String? = null,
    ): Serializable

    override fun getId() = IdClass(this.tag.id, this.lecture.id)

    override fun isNew(): Boolean = this.createdAt == null

}