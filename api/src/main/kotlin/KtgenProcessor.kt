package net.echonolix.ktgen

import java.nio.file.Path

/**
 * Interface for processing files in the ktgen runtime.
 *
 * This interface is used to define a processor that can process input files and generate output files.
 * The processor is expected to be registered as a service in the `META-INF/services` directory.
 */
interface KtgenProcessor {
    /**
     * @param inputs Input file paths
     * @param outputDir Output directory
     *
     * @return Updated file paths
     */
    fun process(inputs: Set<Path>, outputDir: Path): Set<Path>
}