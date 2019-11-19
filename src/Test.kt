import models.User
import kotlin.reflect.jvm.reflect

fun main() {
    val t: ((id: Long, name: String, last: String, count: Int, offset: Int) -> User) = {
        id, name, last, count, offset -> User(name)
    }
    val c = t::class
    println(c)
}