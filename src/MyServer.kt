import controllers.*
import dao.UserDao
import plugins.pug.PugPlugin
import plugins.pug.render
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie

@WebServlet("/*", loadOnStartup = 1)
class MyServer : Server() {

    override val routes = routing {

        intercept {
            if (!isAuthOk) {
                val cookie = cookies["uid"]
                if (cookie != null && cookie.value.isNotEmpty())
                    session["USER"] = UserDao.getByUUID(cookie.value) as Any?
            }
        }
        get("/") {
            if (isAuthOk)
                render("index", mapOf("login" to user.username))
            else
                render("login")
        }
        post("/") {
            val username = params["username"]
            if (username.isNullOrEmpty()) {
                respondText("Please fill the gaps")
                return@post
            }
            if (login(username, "pass")) {
                cookies += Cookie("uid", user.id.toString()).apply { maxAge = 90000 }
                redirect("/")
            } else {
                error("Unauthorized", 401)
            }
        }
        filter {
            if (!isAuthOk) {
                redirect("/")
                return@filter false
            }
            return@filter true
        }

        get("/logout") {
            logout()
            cookies.remove("uid")
            redirect("/")
        }
        router("/user", UsersController())
    }

    init {
        PugPlugin.configure {
            basePath = "/home/niyaz/Projects/SciTech/web"
            isCaching = false
        }
        MyDatabase().init(UserDao)
        UserDao.getById
    }
}