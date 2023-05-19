package fan.yumetsuki.miyako.util

import java.io.File

fun File.listFilesWithExt(vararg exts: String): List<File> {
    val result = mutableListOf<File>()
    if (isDirectory) {
        result.addAll(listFiles().map { it.listFilesWithExt(*exts) }.flatten())
    } else {
        if (exts.isEmpty() || exts.contains(this.extension)) {
            result.add(this)
        }
    }
    return result
}
