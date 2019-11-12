import controllers.UsersController
import plugins.PugPlugin
import plugins.PugPlugin.render
import javax.servlet.annotation.WebServlet

@WebServlet("/*")
class MyServer : Server() {
    override val routes = routing {
        get("/") {
            val login = session["login"] as String?
            if (login == null)
                render("login")
            else
                render("index", mapOf("login" to login))
        }
        post("/") {
            val login = params["login"]
            if (!login.isNullOrEmpty() && login == "niyaz") {
                session["login"] = login
                respondText("Auth ok")
            } else {
                error("Unauthorized", 401)
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