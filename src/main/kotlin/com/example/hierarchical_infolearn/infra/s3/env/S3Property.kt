package com.example.hierarchical_infolearn.infra.s3.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConfigurationProperties("aws")
@ConstructorBinding
data class S3Property (
    val bucketName: String,
    val accessKey: String,
    val secretKey: String,
)
