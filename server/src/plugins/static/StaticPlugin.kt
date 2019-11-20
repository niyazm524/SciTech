package plugins.static

import dsl.RouterBuilder
import routing.StaticContent
import java.io.File

object StaticPlugin {
    internal lateinit var rootFile: File
    fun setRoot(rootPath: String) {
        val root = File(rootPath)
        if (!root.exists() || !root.isDirectory)
            error("Directory ${root.absolutePath} doesn't exists")
        this.rootFile = root
    }
}

fun RouterBuilder.static(path: String, fsPath: String) {
    routes.add(StaticContent(path, fsPath))
}