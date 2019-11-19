package expressions

import Dao
import java.sql.PreparedStatement
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class Insert(val table: String, private val ignoreDuplicate: Boolean = true) {

    private lateinit var statement: PreparedStatement
    lateinit var func: (obj: Any) -> Boolean
    var isInitialized = false

    inline operator fun <reified T : Any> getValue(thisRef: Dao, property: KProperty<*>): Function1<T, Boolean> {
        if (isInitialized) return func
        initVars(thisRef, T::class)
        func = {
            true
        }

        return func

    }

    fun initVars(thisRef: Dao, dataClass: KClass<*>) {
        val paramNames = dataClass.members.map { it.name }
        statement = thisRef.dataSource.connection.prepareStatement(
            """
            INSERT ${if (ignoreDuplicate) "IGNORE " else ""} INTO $table (${paramNames.joinToString(", ")})
                VALUES (${paramNames.map { "?" }});
        """.trimIndent()
        )
        isInitialized = true
    }

    fun insert(): Boolean {
        return false
    }

}