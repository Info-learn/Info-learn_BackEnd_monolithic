package com.example.hierarchical_infolearn.global.base.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseAuthorEntity: BaseTimeEntity() {

    @CreatedBy
    var createdBy: String? = null
        protected set

    @LastModifiedBy
    var updatedBy: String? = null
        protected set


}