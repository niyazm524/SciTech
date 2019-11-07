package routing

import Call
import javax.servlet.http.HttpServletRequest

class Interceptor(val block: Call.() -> Unit) : Route {
    override fun matches(req: HttpServletRequest) = true

    override fun handle(call: Call): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}