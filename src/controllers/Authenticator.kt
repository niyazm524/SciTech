package controllers

import Call
import dao.UserDao
import models.User
import java.security.MessageDigest

val Call.user: User
    get() = this.session["USER"] as User

val Call.userOptional: User?
    get() {
        val u = this.session["USER"]
        return if (u is User) u else null
    }

val Call.isAuthOk: Boolean
    get() = this.userOptional != null

fun Call.login(username: String, password: String): Boolean {
    val hash = hashPass(password)
    val user = UserDao.getWithCredentials(username, hash)
    if (user != null) {
        session["USER"] = user
        return true
    }
    return false
}

fun Call.register(params: Map<String, String>): Boolean {
    val user = User.from(params).let { it.copy(password = hashPass(it.password)) }
    return UserDao.createUser(user)
}

fun Call.logout() {
    session.remove("USER")
}

fun hashPass(password: String): String {
    val md = MessageDigest.getInstance("SHA-1")
    md.update(password.toByteArray())
    val mdbytes = md.digest()
    val sb = StringBuffer()
    for (j in mdbytes.indices) {
        var s = Integer.toHexString(0xff and mdbytes[j].toInt())
        s = if (s.length == 1) "0$s" else s
        sb.append(s)
    }
    return sb.toString()
}