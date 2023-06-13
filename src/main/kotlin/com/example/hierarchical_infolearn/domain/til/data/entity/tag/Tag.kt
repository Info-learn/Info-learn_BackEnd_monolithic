package com.example.hierarchical_infolearn.domain.til.data.entity.tag

import com.example.hierarchical_infolearn.domain.lecture.data.entity.tag.TagUsage
import com.example.hierarchical_infolearn.global.base.entity.BaseAuthorEntity
import javax.persistence.*

@Entity(name = "til_tag")
class Tag(
    name: String,
): BaseAuthorEntity() {

    @Id
    @Column(nullable = false, length = 20, unique = true, columnDefinition = "varchar(20) binary")
    val id: String = name

    @Column(name = "usage_count", nullable = false, unique = false)
    var usageCount: Long = 0
        protected set

    @OneToMany(mappedBy = "tag")
    var tagUsage: MutableList<TagUsage> = ArrayList()
        protected set

    fun increaseUsageCount() {
        this.usageCount += 1
    }
    fun decreaseUsageCount() {
        this.usageCount -= 1
    }
}