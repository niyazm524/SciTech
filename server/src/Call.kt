import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class Call internal constructor(val servletRequest: HttpServletRequest, val servletResponse: HttpServletResponse)
