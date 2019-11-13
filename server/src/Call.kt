import session.Session
import utils.ServerMarker
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ServerMarker
class Call internal constructor(val servletRequest: HttpServletRequest, val servletResponse: HttpServletResponse) {
    private var isClosed = false
    val path: String get() = servletRequest.pathInfo
    val queryString: String? get() = servletRequest.queryString
    val fullPath: String get() = if (queryString != null) "$path?$queryString" else path
    val params = servletRequest.parameterMap.mapValues { it.value[0] }
    private var _contentType = "text/html"
    var contentType: String
        get() = _contentType
        set(value) {
            _contentType = value
            servletResponse.contentType = "$value; charset=UTF-8"
        }
    val session: Session by lazy { return@lazy Session(servletRequest.session) }

    init {
        servletRequest.characterEncoding = "UTF-8"
        servletResponse.characterEncoding = "UTF-8"
        contentType = "text/html"
    }

    private inline fun ifNotClosed(block: () -> Unit) {
        if (!isClosed) block()
    }

    fun respondText(text: String) = ifNotClosed {
        contentType = "text/plain"
        servletResponse.writer.use {
            it.print(text)
        }
        isClosed = true
    }

    fun respondHtml(html: String) = ifNotClosed {
        contentType = "text/html"
        servletResponse.writer.use {
            it.print(html)
        }
        isClosed = true
    }

    fun redirect(newLocation: String, permanent: Boolean = false) = ifNotClosed {
        servletResponse.setHeader("Location", newLocation)
        servletResponse.status = if (permanent) 301 else 302
        isClosed = true
    }

    fun status(statusCode: Int) = ifNotClosed {
        servletResponse.status = statusCode
    }

    fun header(headerName: String, headerValue: String) = ifNotClosed {
        servletResponse.setHeader(headerName, headerValue)
    }

    fun error(message: String, code: Int = 502) = ifNotClosed {
        servletResponse.sendError(code, message)
    }

    internal fun onError(error: Exception) {
        servletResponse.writer.use {
            it.println("<h1>Error occurred: <i>${error.message}</i></h1>")
            it.println("<h3>Stack Trace:</h3>")
            it.println(error.stackTrace.joinToString("<br />"))
        }
    }
}
