import dsl.RouterBuilder
import routing.Route
import utils.ServerMarker
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ServerMarker
abstract class Server : HttpServlet() {
    protected open val routes: List<Route> = listOf()

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
        call.respondText("<h2>URL ${call.path} не найден на сервере.</h2>")
    }
}