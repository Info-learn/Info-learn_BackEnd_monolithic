package com.example.hierarchical_infolearn.global.config.mongo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import javax.sql.DataSource

class DatabaseConfig(
    private val env : Environment
) {

    companion object {
        const val MONGO_DB = "mongoDb"
        const val MONGO_JDBC_TEMPLATE = "mongoJdbcTemplate"
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    fun defaultDatasource(): DataSource =
        DataSourceBuilder.create()
            .driverClassName(env.getProperty("spring.datasource.driver-class-name"))
            .url(env.getProperty("spring.datasource.url"))
            .build()
}