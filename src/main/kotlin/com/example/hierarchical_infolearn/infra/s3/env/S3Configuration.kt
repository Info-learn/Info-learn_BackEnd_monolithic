package com.example.hierarchical_infolearn.infra.s3.env

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.internal.signing.DefaultS3Presigner
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
class S3Configuration {
    @Bean
    fun getS3Presigner(): S3Presigner {
        return DefaultS3Presigner.builder()
            .credentialsProvider(
                ProfileCredentialsProvider.create("infolearn")
            )
            .region(Region.AP_NORTHEAST_2)
            .build()
    }
}