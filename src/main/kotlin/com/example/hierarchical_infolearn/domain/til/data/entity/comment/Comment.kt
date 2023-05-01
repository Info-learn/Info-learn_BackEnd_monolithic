package com.example.hierarchical_infolearn.domain.til.data.entity.comment

import com.example.hierarchical_infolearn.domain.til.data.entity.Til
import com.example.hierarchical_infolearn.global.base.entity.BaseAuthorEntity
import javax.persistence.*

@Entity(name = "tbl_comment")
@Table(name = "tbl_comment")
class Comment(
    til: Til,
    content: String
): BaseAuthorEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "content",length = 50 ,nullable = false, unique = false)
    val content: String = content

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "til_id")
    var til: Til = til

}