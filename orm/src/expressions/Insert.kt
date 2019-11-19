package expressions

import Dao
import java.sql.PreparedStatement
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class Insert(private val table: String, private val ignoreDuplicate: Boolean = true) {

    private lateinit var statement: PreparedStatement
    private lateinit var getters: List<KProperty1.Getter<out Any, Any?>>
    var isInitialized = false

    inline operator fun <reified T : Any> getValue(thisRef: Dao, property: KProperty<*>): Function1<T, Boolean> {
        if (!isInitialized)
            initVars(thisRef, T::class)
        return this::insert
    }

    fun initVars(thisRef: Dao, dataClass: KClass<*>) {
        val paramNames = dataClass.memberProperties.map { it.name }
        getters = dataClass.memberProperties.map { it.getter }
        statement = thisRef.dataSource.connection.prepareStatement(
            """
            INSERT ${if (ignoreDuplicate) "IGNORE " else " "}INTO $table (${paramNames.joinToString(", ")})
                VALUES (${List(paramNames.size) { "?" }.joinToString(", ")});
        """.trimIndent().also { println(it) }
        )
        isInitialized = true
    }

    fun insert(obj: Any): Boolean {
        statement.clearParameters()
        getters
            .forEachIndexed { index, arg ->
                statement.setObject(index + 1, arg.call(obj))
            }
        return statement.executeUpdate() == 1
    }

}