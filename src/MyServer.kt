import controllers.*
import dao.UserDao
import plugins.pug.PugPlugin
import plugins.pug.render
import plugins.static.StaticPlugin
import plugins.static.static
import java.security.MessageDigest
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie

@WebServlet("/*", loadOnStartup = 1)
class MyServer : Server() {

    override val routes = routing {
        static("/css/", "css")
        intercept {
            if (!isAuthOk) {
                val cookie = cookies["uid"]
                if (cookie != null && cookie.value.isNotEmpty())
                    session["USER"] = UserDao.getById(cookie.value.toLong()) as Any?
            }
        }
        get("/") {
            if (isAuthOk)
                render("index", mapOf("login" to user.username))
            else //respondText("webPath: ${servletContext.getRealPath("")}")
                render("login")
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
        router("/register", RegisterController())
        filter {
            if (path.endsWith(".css")) return@filter true
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

    override fun init() {
        super.init()
        PugPlugin.configure {
            basePath = webPath
            isCaching = false
            isPrettyPrint = true
        }
        StaticPlugin.setRoot(webPath)
        MyDatabase().init(UserDao)
    }
}