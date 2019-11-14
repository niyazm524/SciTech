package models

data class User(val username: String) {
    val id = nextId()
}

var lastId = 0
fun nextId() = ++lastId