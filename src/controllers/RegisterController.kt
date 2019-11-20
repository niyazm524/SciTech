package controllers

import plugins.pug.render
import routing.Router

class RegisterController : Router() {
    override val routes = routing {
        get("/") {
            render("register")
        }
    }
}