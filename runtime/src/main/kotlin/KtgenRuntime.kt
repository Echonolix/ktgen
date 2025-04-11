package net.echonolix.ktgen

import java.io.File
import java.nio.file.Paths
import java.util.*

object KtgenRuntime {
    @JvmStatic
    fun main(args: Array<String>) {
        val inputs = args[1].split(File.pathSeparator).map { Paths.get(it) }
        val outputDir = Paths.get(args[0])
        with(outputDir.toFile()) {
            deleteRecursively()
            mkdirs()
        }
        val services = ServiceLoader.load(KtgenProcessor::class.java)
        for (service in services) {
            service.process(inputs, outputDir)
        }
    }
}