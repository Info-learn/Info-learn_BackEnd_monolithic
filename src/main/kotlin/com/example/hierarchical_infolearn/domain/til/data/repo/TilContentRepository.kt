package com.example.hierarchical_infolearn.domain.til.data.repo

import com.example.hierarchical_infolearn.domain.til.data.entity.content.Content
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TilContentRepository: MongoRepository<Content, ObjectId> {
}