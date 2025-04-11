package net.echonolix.ktgen

import java.nio.file.Path

interface KtgenProcessor {
    fun process(inputs: List<Path>, outputDir: Path)
}