allprojects {
    group = "org.echonolix"
    version = "1.0.0"
}

plugins {
    `kotlin-dsl`
    `maven-publish`
}

allprojects {
    apply {
        plugin("org.gradle.kotlin.kotlin-dsl")
        plugin("maven-publish")
    }

    repositories {
        mavenCentral()
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    }
}

subprojects {
    base {
        archivesName.set("${rootProject.name}-${project.name}")
    }
}

java {
    withSourcesJar()
}

gradlePlugin {
    plugins {
        create("ktgen") {
            id = "org.echonolix.ktgen"
            displayName = "ktgen"
            implementationClass = "org.echonolix.ktgen.KtgenPlugin"
        }
    }
}

tasks {
    processResources {
        expand("version" to project.version)
    }
}