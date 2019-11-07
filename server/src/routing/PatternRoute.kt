package routing

import javax.servlet.http.HttpServletRequest

abstract class PatternRoute : Route {
    abstract val method: String
    abstract val pattern: String

    override fun matches(req: HttpServletRequest): Boolean {
        if (method != "*" && req.method != method) return false
        //TODO: advanced glob pattern matching
        return true
    }
}