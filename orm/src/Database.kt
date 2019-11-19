import org.apache.tomcat.jdbc.pool.DataSource
import org.apache.tomcat.jdbc.pool.PoolProperties

abstract class Database {
    open val driverClassName: String = "com.mysql.cj.jdbc.Driver"
    abstract val url: String
    abstract val username: String
    abstract val password: String
    private val dataSource = DataSource()

    fun init(vararg daos: Dao, init: PoolProperties.() -> Unit = {}) {
        val props = PoolProperties()
        props.url = url
        props.driverClassName = driverClassName
        props.username = username
        props.password = password
        props.jdbcInterceptors = "org.apache.tomcat.jdbc.pool.interceptor.StatementCache"
        props.init()
        dataSource.poolProperties = props
        daos.forEach { dao ->
            dao.dataSource = dataSource
        }
    }
}