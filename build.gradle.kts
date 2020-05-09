import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.71"
	kotlin("plugin.spring") version "1.3.71"
}

group = "br.com.onsmarttech"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

springBoot {
	mainClassName = "br.com.onsmarttech.thebutler.ThebutlerApplicationKt"
}

repositories {
	mavenCentral()
	maven(url = "http://jaspersoft.jfrog.io/jaspersoft/third-party-ce-artifacts/")
}

val springCloudVersion = "Hoxton.SR3"
val springSecurityOauth2Version = "2.4.0.RELEASE"
val springSecurityJwtVersion = "1.1.0.RELEASE"
val jasperReportsVersion = "6.12.2"
val gsonVersion = "2.7"

dependencies {
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.security.oauth:spring-security-oauth2:${springSecurityOauth2Version}")
	implementation("org.springframework.security:spring-security-jwt:${springSecurityJwtVersion}")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.amazonaws:aws-java-sdk:1.11.762")
	implementation("net.sf.jasperreports:jasperreports:${jasperReportsVersion}")
	implementation("com.google.code.gson:gson:$gsonVersion")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("org.springframework.security:spring-security-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}