package expressions

import Dao
import annotations.Alias
import annotations.Ignore
import annotations.Table
import java.sql.PreparedStatement
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties

class Insert(private val ignoreDuplicate: Boolean = true) {

    private lateinit var table: String
    private lateinit var statement: PreparedStatement
    private lateinit var getters: List<KProperty1.Getter<out Any, Any?>>
    var isInitialized = false

    inline operator fun <reified T : Any> getValue(thisRef: Dao, property: KProperty<*>): Function1<T, Boolean> {
        if (!isInitialized)
            initVars(thisRef, T::class)
        return this::insert
    }

    fun initVars(thisRef: Dao, dataClass: KClass<*>) {
        table = dataClass.findAnnotation<Table>()?.tableName
            ?: dataClass.simpleName
                    ?: error("Can't get $dataClass's table name")
        val fields = dataClass.memberProperties.filter { !it.hasAnnotation<Ignore>() }
        val paramNames = fields.map { it.findAnnotation<Alias>()?.alias ?: it.name }.map { "`$it`" }
        getters = fields.map { it.getter }
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