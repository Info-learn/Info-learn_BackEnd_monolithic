package com.example.hierarchical_infolearn.global.config.openApi

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(info = Info(title = "Infolearn-Backend-API-DOCS"))
@Configuration
class OpenApiConfig {

    @Bean
    fun authOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("인증")
            .pathsToMatch("/api/infolearn/v1/auth/**")
            .build()
    }

    @Bean
    fun lectureOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("강의")
            .pathsToMatch("/api/infolearn/v1/lecture/**")
            .build()
    }

    @Bean
    fun chapterOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("챕터")
            .pathsToMatch("/api/infolearn/v1/chapter/**")
            .build()
    }

    @Bean
    fun videoOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("영상")
            .pathsToMatch("/api/infolearn/v1/video/**")
            .build()
    }

    @Bean
    fun tilOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("til")
            .pathsToMatch("/api/infolearn/v1/til/**")
            .build()
    }
}