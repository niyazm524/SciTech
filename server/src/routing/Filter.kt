package routing

import Call
import javax.servlet.http.HttpServletRequest

class Filter(val proceed: Call.() -> Boolean) : Route {
    override fun matches(req: HttpServletRequest, path: String) = true

    override fun handle(call: Call) = !proceed(call)
}