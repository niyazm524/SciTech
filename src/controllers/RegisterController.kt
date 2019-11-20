package controllers

import dao.UserDao
import models.User
import plugins.pug.render
import routing.Router

class RegisterController : Router() {
    override val routes = routing {
        get("/") {
            render("register")
        }

        post("/") {
            if (params["agreement"] != "true") {
                render("register", mapOf("message" to "Примите соглашения"))
                return@post
            }
            val user = User.from(params)
            if (UserDao.createUser(user)) {
                redirect("/login")
            } else {
                render("register", mapOf("message" to "Пользователь не создан"))
            }
        }
    }
}