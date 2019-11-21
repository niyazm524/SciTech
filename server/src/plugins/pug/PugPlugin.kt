package plugins.pug

import Call
import de.neuland.jade4j.JadeConfiguration
import java.io.File

object PugPlugin : JadeConfiguration() {
    fun configure(block: JadeConfiguration.() -> Unit) = apply(block)
}


fun Call.render(templateFile: String, model: Map<String, *>? = null) {
    try {
        PugPlugin.renderTemplate(
            PugPlugin.getTemplate(File(PugPlugin.basePath, templateFile).toString()),
            model?.plus("call" to this) ?: hashMapOf("call" to this) as Map<String, Any>?,
            servletResponse.writer
        )
    } catch (e: Exception) {
        e.printStackTrace(System.err)
    }

}