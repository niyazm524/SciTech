package routing

import Call

class RouteImpl(override val method: String, override val pattern: String, val handler: Call.() -> Unit) :
    PatternRoute() {

    override fun handle(call: Call): Boolean {
        call.handler()
        return true
    }
}