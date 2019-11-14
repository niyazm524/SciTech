package controllers

import Call
import dao.UserDao
import models.User

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
    val user = UserDao.getWithCredentials(username, password)
    if (user != null) {
        session["USER"] = user
        return true
    }
    return false
}

fun Call.logout() {
    session.remove("USER")
}