dependencies {
    val kotlinPoetVersion: String by project
    implementation(project(":ktgen-api"))
    runtimeOnly("com.squareup:kotlinpoet:$kotlinPoetVersion")
}

publishing {
    publications {
        create<MavenPublication>("ktgen-runtime") {
            from(components["java"])
        }
    }
}