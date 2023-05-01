package com.example.hierarchical_infolearn.domain.lecture.data.entity.tag

import com.example.hierarchical_infolearn.domain.lecture.data.entity.Lecture
import com.example.hierarchical_infolearn.global.base.entity.BaseTimeEntity
import org.springframework.data.domain.Persistable
import javax.persistence.*

@Entity(name = "tbl_lecture_tag_usage")
@Table(name = "tbl_lecture_tag_usage")
@IdClass(TagUsageIdClass::class)
class TagUsage(
    tag: Tag,
    lecture: Lecture
): BaseTimeEntity(), Persistable<String>, java.io.Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    val tag: Tag = tag

    @Id
    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    val lecture: Lecture = lecture

    override fun getId(): String? {
        return this.id
    }

    override fun isNew(): Boolean {
        return this.createdAt == null
    }

}