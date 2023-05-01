package com.example.hierarchical_infolearn.domain.lecture.data.entity.tag

import com.example.hierarchical_infolearn.domain.lecture.business.dto.response.tag.TagNameResponse
import com.example.hierarchical_infolearn.global.base.entity.BaseAuthorEntity
import javax.persistence.*

@Entity(name = "tbl_lecture_tag")
@Table(name = "tbl_lecture_tag")
class Tag(
    name: String,
): BaseAuthorEntity() {

    @Id
    @Column(nullable = false, length = 20, unique = true)
    val id: String = name

    @Column(name = "usage_count", nullable = false, unique = false)
    var usageCount: Long = 0
        protected set

    @OneToMany
    var tagUsage: MutableList<TagUsage> = ArrayList()
        protected set

    fun toTagNameResponse(): TagNameResponse {
        return TagNameResponse(
            this.id,
        )
    }
}