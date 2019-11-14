package plugins.pug

import Call
import de.neuland.jade4j.JadeConfiguration
import java.io.File

object PugPlugin : JadeConfiguration() {
    fun configure(block: JadeConfiguration.() -> Unit) = apply(block)
}


fun Call.render(templateFile: String, model: Map<String, Any>? = null) {
    PugPlugin.renderTemplate(
        PugPlugin.getTemplate(File(PugPlugin.basePath, templateFile).toString()),
        model ?: hashMapOf(),
        servletResponse.writer
    )
}