import controllers.*
import dao.UserDao
import plugins.pug.PugPlugin
import plugins.static.StaticPlugin
import plugins.static.static
import javax.servlet.annotation.WebServlet

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
        router("/", HomeController())
        router("/login", LoginController())
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