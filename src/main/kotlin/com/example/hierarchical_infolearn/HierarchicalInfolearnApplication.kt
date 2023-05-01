package com.example.hierarchical_infolearn

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableMongoRepositories
class HierarchicalInfolearnApplication

fun main(args: Array<String>) {
	runApplication<HierarchicalInfolearnApplication>(*args)
}
