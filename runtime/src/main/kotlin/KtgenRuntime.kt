package net.echonolix.ktgen

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.PathWalkOption
import kotlin.io.path.deleteExisting
import kotlin.io.path.deleteRecursively
import kotlin.io.path.fileSize
import kotlin.io.path.isDirectory
import kotlin.io.path.walk

object KtgenRuntime {
    tailrec fun addParentUpTo(curr: Path?, end: Path, output: MutableCollection<Path>) {
        if (curr == null) return
        if (curr == end) return
        output.add(curr)
        return addParentUpTo(curr.parent, end, output)
    }
    @OptIn(ExperimentalPathApi::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputs = args[1].split(File.pathSeparator).mapTo(mutableSetOf()) { Paths.get(it) }
        val outputDir = Paths.get(args[0])
        val outputDirFile = outputDir.toFile()
        outputDirFile.mkdirs()
        val services = ServiceLoader.load(KtgenProcessor::class.java)
        val updatedFiles = mutableSetOf<Path>()
        for (service in services) {
            updatedFiles += service.process(inputs, outputDir)
        }
        updatedFiles.toList()
            .forEach {
                addParentUpTo(it.parent, outputDir, updatedFiles)
            }
        outputDir.walk(PathWalkOption.INCLUDE_DIRECTORIES, PathWalkOption.BREADTH_FIRST, PathWalkOption.FOLLOW_LINKS)
            .filter { it != outputDir }
            .filter { it !in updatedFiles }
            .forEach {
                it.deleteRecursively()
            }
    }
}