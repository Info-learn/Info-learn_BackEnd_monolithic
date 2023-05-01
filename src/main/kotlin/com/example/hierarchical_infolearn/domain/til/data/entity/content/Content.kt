package com.example.hierarchical_infolearn.domain.til.data.entity.content

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "content")
class Content(
    id: ObjectId,
    tilId: Long,
    content: String

) {
    @Id
    var id: ObjectId = id
        protected set

    @Field
    val tilId: Long = tilId

}