package controllers

import plugins.pug.render
import routing.Router

class LoginController : Router() {
    override val routes = routing {
        get("/") {
            render("login")
        }

        post("/") {
            val username = requireNotNull(params["username"]) { "No username" }
            val password = requireNotNull(params["password"]) { "Please provide password" }
            login(username, password)
        }
    }
}