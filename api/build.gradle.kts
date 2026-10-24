java {
    withSourcesJar()
}

dependencies {
    val kotlinPoetVersion = providers.gradleProperty("kotlinPoetVersion").get()
    api("com.squareup:kotlinpoet:$kotlinPoetVersion")
}

publishing {
    publications {
        create<MavenPublication>("ktgen-api") {
            from(components["java"])
        }
    }
}
