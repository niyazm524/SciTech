package controllers

import plugins.pug.render
import routing.Router

class LoginController : Router() {
    override val routes = routing {
        get("/") {
            render("login")
        }
    }
}