allprojects {
    group = "org.echonolix"
    version = "1.0.0"
}

plugins {
    `kotlin-dsl`
    `maven-publish`
}

subprojects {
    apply {
        plugin("java")
        plugin("kotlin")
    }
}

allprojects {
    apply {
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