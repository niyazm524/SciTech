import dsl.RouterBuilder
import routing.Route
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class Server : HttpServlet() {
    protected open val routes: List<Route> = listOf()
    lateinit var webPath: String

    override fun service(req: HttpServletRequest, resp: HttpServletResponse) {
        val call by lazy { return@lazy Call(req, resp) }
        var callHandled = false
        try {
            for (route in routes) {
                if (route.matches(req) && route.handle(call)) {
                    callHandled = true
                    break
                }
            }
            if (!callHandled) {
                onNotFound(call)
            }
        } catch (error: Exception) {
            call.onError(error)
        }

    }

    protected fun routing(block: RouterBuilder.() -> Unit): List<Route> {
        val builder = RouterBuilder()
        builder.block()
        return builder.build()
    }

    protected fun onNotFound(call: Call) {
        call.respondHtml("<h2>URL ${call.path} не найден на сервере.</h2>")
    }

    override fun init() {
        super.init()
        webPath = servletContext.getRealPath("")
    }
}