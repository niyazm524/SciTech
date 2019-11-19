import expressions.Select
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

data class Test(val id: Int, val name: String, val age: Int? = -1)

object TestDao: Dao() {
    val getByUsername: (username: String?) -> Test? by Select("SELECT * FROM users WHERE name = ? LIMIT 1")
}
class TestDatabase : Database() {
    override val url = "jdbc:mysql://localhost/scitech?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC"
    override val username = "root"
    override val password = "roulette"
}

fun main() {
    val db = TestDatabase()
    db.init(TestDao)
    measureTimeMillis { TestDao.getByUsername("Niyaz") }.let { println("Cold start: $it ms") }
    measureTimeMillis { TestDao.getByUsername(null) }.let { println("Hot start: $it ms") }
    println(TestDao.getByUsername("Niyaz"))
}