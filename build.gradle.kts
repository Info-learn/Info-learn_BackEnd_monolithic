import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.3"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	kotlin("plugin.jpa") version "1.7.22"
	kotlin("kapt") version "1.7.22"
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("org.springframework.data.redis.core.RedisHash")
	annotation("org.springframework.data.mongodb.core.mapping.Document")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.EmbeddedId")
	annotation("org.springframework.data.elasticsearch.annotations.Document")
}

noArg {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("org.springframework.data.redis.core.RedisHash")
	annotation("org.springframework.data.mongodb.core.mapping.Document")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.EmbeddedId")
	annotation("org.springframework.data.elasticsearch.annotations.Document")
}


group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude(module = "spring-boot-starter-tomcat")
	}
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	//Mysql
	runtimeOnly("mysql:mysql-connector-java:8.0.30")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	//undertow
	implementation("org.springframework.boot:spring-boot-starter-undertow")
	//Validation
	implementation("org.springframework.boot:spring-boot-starter-validation:3.0.4")
	//Security
	implementation("org.springframework.boot:spring-boot-starter-security")
	//Jwts
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	//data jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	//Jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	//mailing
	implementation("org.springframework.boot:spring-boot-starter-mail")
	//thymeleaf
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	//redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	//spring-doc
	implementation("org.springdoc:springdoc-openapi-ui:1.6.15")
	implementation("org.springdoc:springdoc-openapi-security:1.6.15")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.6.15")
	//AWS
	implementation(platform("software.amazon.awssdk:bom:2.20.26"))
	implementation("software.amazon.awssdk:s3")
	//Mongo
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	//Querydsl
	implementation("com.querydsl:querydsl-jpa:5.0.0")
	kapt("com.querydsl:querydsl-apt:5.0.0:jpa")
	//netty-socketio
	implementation("com.corundumstudio.socketio:netty-socketio:1.7.19")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.getByName<Jar>("jar") {
	enabled = false
}
