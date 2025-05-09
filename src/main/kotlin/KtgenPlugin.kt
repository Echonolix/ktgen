package net.echonolix.ktgen

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.*

class KtgenPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val ktgenExtension = project.extensions.create<KtgenExtension>("ktgen", project)
        val sourceSetContainer = project.extensions.getByType(SourceSetContainer::class.java)

        val ktgenInput = project.configurations.create("ktgenInput")
        val ktgenImpl = project.configurations.create("ktgen")

        project.dependencies.add("ktgen", "net.echonolix:ktgen-api:$version")
        project.dependencies.add("ktgen", "net.echonolix:ktgen-runtime:$version")

        project.afterEvaluate {
            sourceSetContainer.named("main").configure {
                if (ktgenExtension.defaultCompile.get()) {
                    java.srcDir(ktgenExtension.outputDir)
                    project.tasks.named(getCompileTaskName("kotlin")).configure {
                        dependsOn("ktgen")
                    }
                }
            }
        }

        val ktgenTask = project.tasks.register<KtgenTask>("ktgen") {
            inputFiles.set(ktgenInput)
            runtimeClasspath.set(ktgenImpl)
            outputDir.set(ktgenExtension.outputDir)
        }

        project.afterEvaluate {
            project.tasks.findByName("sourcesJar")?.dependsOn(ktgenTask)
        }
    }

    companion object {
        val version = KtgenPlugin::class.java.getResource("/ktgen-plugin.version")!!.readText()
    }
}