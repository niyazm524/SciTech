package dsl

import Call
import routing.Interceptor
import routing.Route
import routing.RouteImpl
import routing.Router
import utils.ServerMarker

@ServerMarker
class RouterBuilder {
    private val routes = mutableListOf<Route>()

    fun get(path: String, block: Call.() -> Unit) {
        routes.add(RouteImpl("GET", path, block))
    }

    fun post(path: String, block: Call.() -> Unit) {
        routes.add(RouteImpl("POST", path, block))
    }

    fun put(path: String, block: Call.() -> Unit) {
        routes.add(RouteImpl("PUT", path, block))
    }

    fun delete(path: String, block: Call.() -> Unit) {
        routes.add(RouteImpl("DELETE", path, block))
    }

    fun any(path: String, block: Call.() -> Unit) {
        routes.add(RouteImpl("*", path, block))
    }

    fun router(path: String, router: Router) {
        router.pathPart = path
        routes.add(router)
    }

    fun intercept(block: Call.() -> Unit) {
        routes.add(Interceptor(block))
    }

    internal fun build() = routes.toList()
}