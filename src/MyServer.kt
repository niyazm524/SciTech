import controllers.UsersController
import models.User
import plugins.PugPlugin
import plugins.PugPlugin.render
import javax.servlet.annotation.WebServlet

@WebServlet("/*")
class MyServer : Server() {
    override val routes = routing {
        get("/") {
            val user = session["user"] as User?
            if (user == null)
                render("login")
            else
                render("index", mapOf("login" to user.username))
        }
        post("/") {
            val login = params["login"]
            if (!login.isNullOrEmpty() && login == "niyaz") {
                session["user"] = User(login)
                redirect("/")
            } else {
                error("Unauthorized", 401)
            }
        }
        get("/logout") {
            val user = session["user"] as User?
            if (user != null) {
                session.remove("user")
                redirect("/")
            }
        }
        router("/user", UsersController())
    }

    init {
        PugPlugin.configure {
            basePath = "/home/niyaz/Projects/SciTech/web"
            isCaching = false
        }
    }
}