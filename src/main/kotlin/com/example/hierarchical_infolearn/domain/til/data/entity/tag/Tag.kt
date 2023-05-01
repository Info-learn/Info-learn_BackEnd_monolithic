package com.example.hierarchical_infolearn.domain.til.data.entity.tag

import com.example.hierarchical_infolearn.domain.lecture.data.entity.tag.TagUsage
import com.example.hierarchical_infolearn.global.base.entity.BaseAuthorEntity
import java.util.*
import javax.persistence.*

@Entity(name = "tbl_til_tag")
@Table(name = "tbl_til_tag")
class Tag(
    id: UUID,
    name: String,
): BaseAuthorEntity() {
    @Id
    val id: UUID = id

    @Column(name = "name" ,nullable = false, length = 20, unique = true)
    val name: String = name

    @OneToMany
    var tagUsage: MutableList<TagUsage> = ArrayList()
        protected set
}