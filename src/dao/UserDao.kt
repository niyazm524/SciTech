package dao

import Dao
import expressions.Select
import models.User

object UserDao : Dao() {
    val getById: (id: Long, name: String, last: String, count: Int, offset: Int) -> User by Select("")

    val getByUUID: (uuid: String) -> User? by Select("")

    val getWithCredentials: (username: String, password: String) -> User?
        by Select("")
}