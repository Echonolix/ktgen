package net.echonolix.ktgen

import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property

abstract class KtgenExtension(project: Project) {
    abstract val defaultCompile: Property<Boolean>
    abstract val outputDir: DirectoryProperty

    init {
        defaultCompile.convention(true).finalizeValueOnRead()
        outputDir.convention(project.layout.buildDirectory.dir("generated/ktgen"))
    }
}