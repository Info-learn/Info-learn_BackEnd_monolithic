package com.example.hierarchical_infolearn.domain.til.data.entity.like

import com.example.hierarchical_infolearn.domain.til.data.entity.Til
import com.example.hierarchical_infolearn.global.base.entity.BaseAuthorEntity
import javax.persistence.*

@Entity(name = "tbl_like")
@Table(name = "tbl_like")
class Like(
    til: Til,
): BaseAuthorEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "til_id", columnDefinition = "BINARY(16)")
    var til: Til = til
}