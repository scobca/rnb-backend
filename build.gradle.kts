plugins {
    kotlin("plugin.serialization") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("kapt") version "1.9.25"
    kotlin("jvm") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("it.nicolasfarabegoli.conventional-commits") version "3.1.3"
}

group = "org.itmo"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springModulithVersion"] = "1.3.4"

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Config & Mappers
    implementation("io.github.cdimascio:dotenv-java:2.2.0")
    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.3.Final")

    // Database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:42.7.2")
    runtimeOnly("org.postgresql:postgresql")

    // Kotlin coroutines
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    // Kotlinx serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    // JWT Auth
    implementation("io.jsonwebtoken:jjwt:0.12.6")
    implementation("javax.xml.bind:jaxb-api:2.3.0")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

conventionalCommits {
    warningIfNoGitRoot = true
    types += listOf("build", "chore", "docs", "feat", "fix", "refactor", "style", "test")
    scopes = emptyList()
    successMessage = "Сообщение коммита соответствует стандартам Conventional Commit."
    failureMessage = "Сообщение коммита не соответствует стандартам Conventional Commit."
 }
