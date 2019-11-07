package routing

import Call
import javax.servlet.http.HttpServletRequest

interface Route {
    fun matches(req: HttpServletRequest): Boolean
    fun handle(call: Call): Boolean
}