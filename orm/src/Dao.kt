import javax.sql.DataSource

abstract class Dao {
    lateinit var dataSource: DataSource
}