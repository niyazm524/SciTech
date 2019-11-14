package routing

import Call
import javax.servlet.http.HttpServletRequest

class Interceptor(val block: Call.() -> Unit) : Route {
    override fun matches(req: HttpServletRequest, path: String) = true

    override fun handle(call: Call): Boolean {
        block(call)
        return false
    }
}