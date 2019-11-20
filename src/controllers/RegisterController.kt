package controllers

import plugins.pug.render
import routing.Router

class RegisterController : Router() {
    override val routes = routing {
        get("/") {
            render("register")
        }

        post("/") {
            onError { render("register", mapOf("message" to it.message)) }
            if (params["agreement"] != "true") kotlin.error("Вы должны принять соглашение для продолжения.")

            if (register(params)) {
                redirect("/login")
            } else {
                kotlin.error("Пользователь не создан")
            }
        }
    }
}