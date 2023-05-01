package com.example.hierarchical_infolearn.global.base.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseTimeEntity {

    @CreatedDate
    @Column(name = "created_at")
    var createdAt: LocalDateTime? = null
        protected set

    @LastModifiedDate
    @Column(name = "update_at")
    var updateAt: LocalDateTime? = null
        protected set
}