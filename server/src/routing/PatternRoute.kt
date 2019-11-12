package routing

import java.util.function.Predicate
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest

abstract class PatternRoute : Route {
    abstract val method: String
    abstract val pattern: String
    private val predicate: Predicate<String> by lazy {
        val out = StringBuilder("^")
        for (c in pattern) {
            when (c) {
                '*' -> out.append(".*")
                '?' -> out.append('.')
                '.' -> out.append("\\.")
                '\\' -> out.append("\\\\")
                else -> out.append(c)
            }
        }
        if (out.last() == '/') out.append('?')
        out.append('$')
        return@lazy Pattern.compile(out.toString(), Pattern.CASE_INSENSITIVE).asPredicate()
    }

    override fun matches(req: HttpServletRequest, path: String): Boolean {
        if (method != "*" && req.method != method) return false
        return predicate.test(path.trimEnd('/'))
    }
}