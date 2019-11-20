package controllers

import plugins.pug.render
import routing.Router

class HomeController : Router() {
    override val routes = routing {
        get("/") {
            render("index")
        }
    }
}