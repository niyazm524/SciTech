package expressions.fabric

import kotlin.reflect.KClass

object Func {
    fun <T> F0(block: (args: List<Any?>?) -> T): () -> T {
        return {
            block(null)
        }
    }
    fun <T> F1(block: (args: List<Any?>?) -> T): (a1: Any?) -> T {
        return {
            block(listOf(it))
        }
    }
    fun <T> F2(block: (args: List<Any?>?) -> T): (a1: Any?, a2: Any?) -> T {
        return { a1, a2 ->
            block(listOf(a1, a2))
        }
    }
    fun <T> F3(block: (args: List<Any?>?) -> T): (a1: Any?, a2: Any?, a3: Any?) -> T {
        return { a1, a2, a3 ->
            block(listOf(a1, a2, a3))
        }
    }
}