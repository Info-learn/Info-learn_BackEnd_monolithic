package com.example.hierarchical_infolearn.global.config.mongo

import com.example.hierarchical_infolearn.global.config.mongo.env.DataSourceProperty
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration

@Configuration
class MongoConfig(
    private val datasourceProperty: DataSourceProperty
): AbstractMongoClientConfiguration(){

    override fun getDatabaseName() = "infolearn"

    override fun mongoClient(): MongoClient =
        MongoClients.create(MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(datasourceProperty.url))
            .build())

    override fun autoIndexCreation() = true
}