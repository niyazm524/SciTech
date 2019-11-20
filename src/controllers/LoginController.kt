package controllers

import plugins.pug.render
import routing.Router
import javax.servlet.http.Cookie

class LoginController : Router() {
    override val routes = routing {
        get("/") {
            render("login")
        }

        post("/") {
            onError { render("login", mapOf("message" to it.message)) }
            val username = requireNotNull(params["username"]) { "No username" }
            val password = requireNotNull(params["password"]) { "Please provide password" }
            if (login(username, password)) {
                if (params["remember"] == "true")
                    cookies += Cookie("uid", user.id.toString()).apply { maxAge = 90000 }
                redirect("/")
            } else kotlin.error("Не сработало. Проверьте данные для входа.")
        }
    }
}