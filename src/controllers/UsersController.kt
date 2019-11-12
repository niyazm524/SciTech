package controllers

import routing.Router

class UsersController : Router() {
    override val routes = routing {
        get("/") {
            respondHtml("<h1>user</h1>")
        }
    }
}