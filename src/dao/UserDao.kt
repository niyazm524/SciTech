package dao

import models.User

object UserDao {
    val users = mutableMapOf(
        1 to User("niyaz")
    )
    val uuids = mutableMapOf<String, Int>()

    fun getByUUID(uuid: String): User? = uuids[uuid]?.let { users[it] }

    fun getWithCredentials(username: String, password: String): User? =
        users.values.firstOrNull { it.username == username }
}