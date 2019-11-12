package session

import javax.servlet.http.HttpSession

class Session internal constructor(private val jSession: HttpSession) {
    val keys: List<String> by lazy { jSession.attributeNames.toList() }
    val isNew: Boolean get() = jSession.isNew
    val id: String get() = jSession.id
    val creationTime get() = jSession.creationTime
    val lastAccessedTime get() = jSession.lastAccessedTime
    var maxInactiveInterval: Int
        get() = jSession.maxInactiveInterval
        set(value) {
            jSession.maxInactiveInterval = value
        }

    operator fun get(key: String): Any? {
        return jSession.getAttribute(key)
    }

    operator fun set(key: String, value: Any) {
        jSession.setAttribute(key, value)
    }

    fun remove(key: String) {
        jSession.removeAttribute(key)
    }

    fun invalidate() = jSession.invalidate()
}