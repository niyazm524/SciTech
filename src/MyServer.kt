import javax.servlet.annotation.WebServlet

@WebServlet("/*")
class MyServer : Server() {
    override val routes = routing {
        get("/home") {
            println("home")
        }
    }
}