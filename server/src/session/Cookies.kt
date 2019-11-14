package session

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

class Cookies(private val cookies: Array<out Cookie>, private val res: HttpServletResponse) {
    operator fun get(key: String) = cookies.firstOrNull { it.name == key }

    operator fun plusAssign(cookie: Cookie) = res.addCookie(cookie)

    fun remove(key: String) = res.addCookie(Cookie(key, "").apply {
        maxAge = 0
    })
}