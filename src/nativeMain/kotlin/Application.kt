
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.fclose
import platform.posix.fgets
import platform.posix.fopen
import platform.posix.perror

// This is needed to trigger the Memory Leak together with reading a relatively large file.
private val resultBundleRegex = "Result: Bundle\\[\\{body=(.*), code=(\\d+)}]".toRegex()

fun main(args: Array<String>) {
    val fileName = "test_file.txt"
    val file = fopen(fileName, "r")
    if (file == null) {
        perror("cannot open input file $fileName")
    }
    try {
        memScoped {
            val bufferLength = 128 * 1024
            val buffer = allocArray<ByteVar>(bufferLength)

            // Reading only the first 3k lines doesn't trigger the Memory Leak
            // for (i in 0..3000) {
            while (true) {
                val nextLine = fgets(buffer, bufferLength, file)?.toKString()
                if (nextLine == null || nextLine.isEmpty()) {
                    break
                }
            }
        }
    } finally {
        fclose(file)
    }
}

