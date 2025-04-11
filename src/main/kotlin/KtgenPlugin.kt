package net.echonolix.ktgen

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer

class KtgenPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val sourceSetContainer = project.extensions.getByType(SourceSetContainer::class.java)
        val srcDir = project.layout.buildDirectory.dir("generated/ktgen")

        val ktgenInput = project.configurations.create("ktgenInput")
        val ktgenImpl = project.configurations.create("ktgen")

        project.dependencies.add("ktgen", "net.echonolix:ktgen-api:$version")
        project.dependencies.add("ktgen", "net.echonolix:ktgen-runtime:$version")

        sourceSetContainer.named("main").configure {
            java.srcDir(srcDir)
            project.tasks.named(getCompileTaskName("kotlin")).configure {
                dependsOn("ktgen")
            }
        }

        val ktgenTask = project.tasks.register("ktgen", KtgenTask::class.java) {
            inputFiles.set(ktgenInput)
            runtimeClasspath.set(ktgenImpl)
            outputDir.set(srcDir)
        }

        project.tasks.findByName("sourcesJar")?.dependsOn(ktgenTask)
    }

    companion object {
        val version = KtgenPlugin::class.java.getResource("/ktgen-plugin.version")!!.readText()
    }
}