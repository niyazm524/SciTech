package dao

import Dao
import expressions.Select
import models.User

object UserDao : Dao() {
    val getById: (id: Long) -> User? by Select("SELECT * FROM users WHERE id = ?")

    val getWithCredentials: (username: String, passHash: String) -> User?
        by Select("")
}