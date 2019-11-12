package routing

import Call
import dsl.RouterBuilder
import javax.servlet.http.HttpServletRequest

open class Router : PatternRoute() {
    internal var pathPart = "/"
    final override val method: String
        get() = "*"
    final override val pattern: String
        get() = pathPart
    protected open val routes = listOf<Route>()

    final override fun handle(call: Call): Boolean {
        val curPath = call.servletRequest.pathInfo.removePrefix(pathPart)
        for (route in routes) {
            if (route.matches(call.servletRequest, curPath) && route.handle(call)) {
                return true
            }
        }
        return false
    }

    final override fun matches(req: HttpServletRequest, path: String) = super.matches(req, path)

    protected final fun routing(block: RouterBuilder.() -> Unit): List<Route> {
        val builder = RouterBuilder()
        builder.block()
        return builder.build()
    }
}