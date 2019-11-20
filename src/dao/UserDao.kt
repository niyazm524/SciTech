package dao

import Dao
import expressions.Insert
import expressions.Select
import models.User

object UserDao : Dao() {
    val getById: (id: Long) -> User? by Select("SELECT * FROM user WHERE id = ?")

    val getWithCredentials: (username: String, passHash: String) -> User?
        by Select("")

    val createUser: (user: User) -> Boolean by Insert()

}