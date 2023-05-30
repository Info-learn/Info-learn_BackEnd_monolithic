package com.example.hierarchical_infolearn.domain.til.data.entity.content

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.UUID

@Document(collection = "content")
class Content(
    id: ObjectId,
    tilId: UUID,
    content: String
) {
    @Id
    var id: ObjectId = id
        protected set

    @Field()
    val tilId: String = tilId.toString()

    var content: String = content

    fun getTilId(): UUID = UUID.fromString(this.tilId)
}