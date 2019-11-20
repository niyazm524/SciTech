package controllers

import dao.UserDao
import plugins.pug.render
import routing.Router
import java.security.MessageDigest
import javax.servlet.http.Cookie

class HomeController : Router() {
    override val routes = routing {
        get("/") {
            render("index")
        }
        post("/") {
            val username = requireNotNull(params["username"]) { "No username" }
            val password = requireNotNull(params["password"]) { "Please provide password" }

            val hash = MessageDigest.getInstance("SHA-256").digest(password.toByteArray()).toString()
            println("hash is $hash")
            val user = requireNotNull(UserDao.getWithCredentials(username, hash)) {
                error("Unauthorized", 401)
            }
            cookies += Cookie("uid", user.id.toString()).apply { maxAge = 90000 }
            redirect("/")
        }
    }
}