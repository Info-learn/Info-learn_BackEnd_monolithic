package com.example.hierarchical_infolearn.global.config.mongo.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("datasource.mongo")
@ConstructorBinding
data class DataSourceProperty(
    val url: String
)