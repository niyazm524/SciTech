import expressions.Insert
import expressions.Select
import kotlin.system.measureTimeMillis

data class Test(val id: Int, val name: String, val age: Int? = -1)

object TestDao: Dao() {
    val getByUsername: (username: String, minAge: Int) -> Test?
            by Select("SELECT * FROM user WHERE name = ? AND age >= ? LIMIT 1")

    val create: (test: Test) -> Boolean by Insert()
}
class TestDatabase : Database() {
    override val url = "jdbc:mysql://localhost/scitech?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC"
    override val username = "root"
    override val password = "roulette"
}

fun main() {
    val db = TestDatabase()
    db.init(TestDao)
    measureTimeMillis { TestDao.getByUsername("Niyaz", 0) }.let { println("Cold start: $it ms") }
    measureTimeMillis { TestDao.getByUsername("neyas", 0) }.let { println("Hot start: $it ms") }
    println(TestDao.getByUsername("Niyaz", 20))
    println(TestDao.create(Test(0, "Test user", null)))
}