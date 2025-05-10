plugins {
    id("java")
    id("org.springframework.boot") version("3.4.1")
    id("io.spring.dependency-management") version("1.1.7")
    id("org.openapi.generator") version("7.6.0")
}

group = "com.vincenzo.bikehub"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(23)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.getByName("annotationProcessor"))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.5")
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.5")
    implementation("org.springframework.boot:spring-boot-starter-security:3.4.5")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.4.5")
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    runtimeOnly("org.postgresql:postgresql:42.7.5")
    implementation ("jakarta.validation:jakarta.validation-api:3.1.1")
    implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.9.10")
    implementation("org.apache.logging.log4j:log4j-core:2.24.3")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

}

java {
    sourceSets {
        getByName("main") {
            java {
                srcDir(layout.buildDirectory.dir("generated/bikehub/src/main/java"))
            }
        }
    }
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generateBikeHub") {
    generatorName.set("spring")

    inputSpec.set("$rootDir/src/main/resources/openapi/bikehub.yaml")
    outputDir.set(layout.buildDirectory.dir("generated/bikehub").get().asFile.absolutePath)
    packageName.set("com.vincenzo.bikehub.server.gen")
    apiPackage.set("com.vincenzo.bikehub.server.gen.controller")
    modelPackage.set("com.vincenzo.bikehub.server.gen.model")

    generateApiDocumentation.set(false)
    generateModelTests.set(false)

    configOptions.put("documentationProvider", "none")
    configOptions.put("interfaceOnly", "true")
    configOptions.put("dateLibrary", "java8")
    configOptions.put("useJakartaEe", "true")
    configOptions.put("skipDefaultInterface", "true")
    configOptions.put("serializableModel", "true")
}


tasks.withType<JavaCompile>().configureEach {
    dependsOn(tasks.named("generateBikeHub"))
}
