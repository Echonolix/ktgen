allprojects {
    group = "net.echonolix"
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

    dependencies {
        api(kotlin("reflect"))
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
            id = "net.echonolix.ktgen"
            displayName = "ktgen"
            implementationClass = "net.echonolix.ktgen.KtgenPlugin"
        }
    }
}

tasks {
    processResources {
        expand("version" to project.version)
    }
}