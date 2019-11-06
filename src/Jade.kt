import de.neuland.jade4j.Jade4J
import de.neuland.jade4j.JadeConfiguration
import de.neuland.jade4j.template.TemplateLoader
import java.nio.file.Paths
import javax.servlet.http.HttpServletResponse

object Jade {
    val conf = JadeConfiguration().apply {
        basePath = "/home/niyaz/Projects/SciTech/web"
        isCaching = false
        println(basePath)
    }

    fun render(filename: String, model: Map<String, Any>?, resp: HttpServletResponse) {
        conf.renderTemplate(
            conf.getTemplate(Paths.get(conf.basePath, filename).toString()),
            model ?: hashMapOf(), resp.writer
        )

    }
}