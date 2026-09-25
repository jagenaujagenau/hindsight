package earth.diego.hindsight.shared

import java.io.File
import java.nio.channels.FileChannel
import java.nio.file.StandardOpenOption

/** Sync a published filename before reporting success or acknowledging receipt. */
fun syncDirectory(directory: File) {
    // FileInputStream rejects directories. NIO opens a directory descriptor on
    // Android/Linux and force(true) calls fsync; failures must reach the caller.
    FileChannel.open(directory.toPath(), StandardOpenOption.READ).use { it.force(true) }
}
