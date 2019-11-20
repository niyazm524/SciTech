package routing

import Call
import org.apache.commons.io.FileUtils
import plugins.static.StaticPlugin
import java.io.File
import java.nio.file.Files
import javax.servlet.http.HttpServletRequest

class StaticContent(val path: String, val fsPath: String) : Route {
    private val root: File by lazy { File(StaticPlugin.rootFile, fsPath) }

    override fun matches(req: HttpServletRequest, path: String): Boolean {
        return req.method == "GET" && path.startsWith(this.path)
                && File(root, path.removePrefix(this.path)).exists()
    }

    override fun handle(call: Call): Boolean {
        val file = File(root, call.path.removePrefix(this.path))
        call.contentType = Files.probeContentType(file.toPath())
        FileUtils.copyFile(file, call.servletResponse.outputStream)
        call.servletResponse.flushBuffer()

        return true
    }
}