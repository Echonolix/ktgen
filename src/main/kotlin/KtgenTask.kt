package net.echonolix.ktgen

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.OutputDirectory

abstract class KtgenTask : JavaExec() {
    @get:InputFiles
    abstract val inputFiles: Property<FileCollection>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @get:InputFiles
    abstract val runtimeClasspath: Property<FileCollection>

    @Internal
    var execWrapper: (KtgenTask) -> Unit = { callExec() }

    init {
        mainClass.set("net.echonolix.ktgen.KtgenRuntime")
    }

    fun callExec() {
         super.exec()
    }

    override fun exec() {
        classpath = runtimeClasspath.get()
        args = listOf(outputDir.asFile.get().absolutePath, inputFiles.get().asPath)
        execWrapper(this)
    }
}