# PoC for Kotlin Native memory leak

This is a proof of concept of a Kotlin native program that exits with error message:

`/opt/teamcity-agent/work/4d622a065c544371/runtime/src/main/cpp/Memory.cpp:1150: runtime assert: Memory leaks found`

The conditions for that to happen are the following:
* Declare a top level variable that uses `toRegex()` (see [Application.kt#L12](src/nativeMain/kotlin/Application.kt#L12))
* Read a file with more than 4000 lines using posix functions (see [Application.kt#L27](https://github.com/jush/regex_memory_leak/blob/master/src/nativeMain/kotlin/Application.kt#L27))

