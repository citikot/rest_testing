plugins {
    java
    eclipse
    id("org.springframework.boot") version "3.2.1" apply false
    id("io.gatling.gradle") version "3.10.3.1" apply false
}

allprojects {
    group = "it.discovery"
}

subprojects {
    apply(plugin = "java")
    //apply(plugin = "org.springframework.boot")

    java.sourceCompatibility = JavaVersion.VERSION_21
    java.targetCompatibility = JavaVersion.VERSION_21

    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
    }

    var springBootVersion = "3.2.1"

    dependencies {
        implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.apache.commons:commons-lang3")

        runtimeOnly("jakarta.xml.bind:jakarta.xml.bind-api")
        runtimeOnly("jakarta.annotation:jakarta.annotation-api")
        runtimeOnly("com.sun.xml.bind:jaxb-impl")
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok:1.18.30")
    }

    tasks.withType<JavaCompile>() {
        options.compilerArgs.addAll(listOf("-parameters", "--enable-preview"))
    }
}

