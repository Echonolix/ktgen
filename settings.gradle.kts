pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

listOf(":api", ":runtime").forEach {
    include(it)
    project(it).name = rootProject.name + it.replace(":", "-")
}