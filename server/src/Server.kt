import dsl.RouterBuilder
import routing.Route
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


abstract class Server : HttpServlet() {
    protected open val routes: List<Route> = listOf()

    override fun service(req: HttpServletRequest, resp: HttpServletResponse) {
        val call by lazy { return@lazy Call(req, resp) }

        for (route in routes) {
            if (route.matches(req) && route.handle(call)) {
                break
            }
        }
    }

    protected fun routing(block: RouterBuilder.() -> Unit): List<Route> {
        val builder = RouterBuilder()
        builder.block()
        return builder.build()
    }
}