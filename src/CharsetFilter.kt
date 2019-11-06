import java.io.IOException
import javax.servlet.*

class CharsetFilter : Filter {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        servletRequest.characterEncoding = "UTF-8"
        servletResponse.characterEncoding = "UTF-8"
        servletResponse.contentType = "text/html; charset=UTF-8"
        filterChain.doFilter(servletRequest, servletResponse)
    }
}
