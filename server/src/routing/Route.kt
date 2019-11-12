package routing

import Call
import javax.servlet.http.HttpServletRequest

interface Route {
    fun matches(req: HttpServletRequest, path: String = req.pathInfo): Boolean
    fun handle(call: Call): Boolean
}