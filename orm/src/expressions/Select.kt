package expressions

import Dao
import expressions.fabric.Func
import org.intellij.lang.annotations.Language
import java.sql.PreparedStatement
import java.sql.ResultSet
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.full.primaryConstructor
import kotlin.system.measureTimeMillis

//@Suppress("UNCHECKED_CAST")
class Select(@Language("MySQL") val sql: String) {

    private lateinit var paramsMap: Map<String, KParameter>
    private lateinit var statement: PreparedStatement
    lateinit var rClass: KClass<*>
    lateinit var func: Function<*>
    var isInitialized = false

    inline operator fun <reified T: Function<*>> getValue(thisRef: Dao, property: KProperty<*>): T {
        if(isInitialized) return func as T
        initVars(thisRef, property)
        func = when(T::class.java) {
            Function0::class.java -> Func.F0(this::selectOne)
            Function1::class.java -> Func.F1(this::selectOne)
            Function2::class.java -> Func.F2(this::selectOne)
            Function3::class.java -> Func.F3(this::selectOne)
            else -> error("Unknown lambda function")
        }
        println(T::class.java.isAssignableFrom(Function1::class.java))
        println(T::class.constructors)
        return func as T

    }

    fun initVars(thisRef: Dao, property: KProperty<*>) {
        rClass = property.returnType.arguments.last().type?.classifier as KClass<*>
        paramsMap = getParamsMap(rClass.primaryConstructor!!.parameters)
        statement = thisRef.dataSource.connection.prepareStatement(sql)
        isInitialized = true
    }

    private fun getParamsMap(params: List<KParameter>): Map<String, KParameter> {
        val map = mutableMapOf<String, KParameter>()
        params.forEach { map[it.name!!] = it }
        return map
    }

    fun selectOne(args: List<Any?>? = null): Any? {
        lateinit var set: ResultSet
        statement.clearParameters()
        args?.forEachIndexed { index, arg ->
            statement.setObject(index+1, arg)
        }
        val time = measureTimeMillis {
            set = statement.executeQuery()
            if(!set.next()) return null

        }
        println("Time elapsed: $time ms")
        return mapToObject(set)
    }

    private fun mapToObject(resultSet: ResultSet): Any {
        val map = mutableMapOf<KParameter, Any?>()
        for (col in 1..resultSet.metaData.columnCount) {
            val colName = resultSet.metaData.getColumnName(col)
            val param = paramsMap[colName]
            if (param != null) {
                map[param] = resultSet.getObject(col)
            }
        }
        return rClass.primaryConstructor!!.callBy(map)
    }
}