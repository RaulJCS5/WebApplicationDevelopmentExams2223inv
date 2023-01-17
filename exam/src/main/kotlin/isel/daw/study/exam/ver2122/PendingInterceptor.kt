package isel.daw.study.exam.ver2122

import isel.daw.study.exam.PendingService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Component
class PendingFilter(
    val pendingService: PendingService
):HttpFilter() {

    companion object {
        private val logger = LoggerFactory.getLogger(PendingFilter::class.java)
    }

    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        /*val dateHour = Date.from(Instant.now()).hours
        val dateMinutes = Date.from(Instant.now()).minutes
        val dateSeconds = Date.from(Instant.now()).seconds*/
        val start = System.nanoTime()
        val handler = request?.requestURI
        try {
            if (handler != null) {
                pendingService.addPending(handler)
            }
            chain?.doFilter(request, response)
        } finally {
            val end = System.nanoTime() - start
            //val handler = request?.getAttribute(PendingInterceptor.KEY) ?: "<unknown>"
            //pendingService.addPending(handler.toString())
            logger.info("time={} handler='{}'", end, handler)
        }
    }
}

class PendingInterceptor : HandlerInterceptor  {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod){
            request.setAttribute(KEY,handler.method.name)
        }
        return true
    }
    companion object {
        const val KEY = "PendingHandleInterceptor"
    }
}

@Configuration
class PendingConfiguration : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(PendingInterceptor())
    }
}

