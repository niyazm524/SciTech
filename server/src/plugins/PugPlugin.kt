package plugins

import Call
import de.neuland.jade4j.JadeConfiguration
import java.io.File

object PugPlugin : JadeConfiguration() {
    fun configure(block: JadeConfiguration.() -> Unit) = apply(block)

    fun Call.render(templateFile: String, model: Map<String, Any>? = null) {
        renderTemplate(
            getTemplate(File(basePath, templateFile).toString()),
            model ?: hashMapOf(),
            servletResponse.writer
        )
    }
}


