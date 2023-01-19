package isel.daw.study.exam

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@RestController("/especial")
class HeaderController {

    @GetMapping("/header")
    fun getHeader():ResponseEntity<*>{
        return ResponseEntity
            .accepted()
            .body("Get header")
    }
}
/*
@Service
class HeaderService{

}
@Component
class HeaderFilter:HttpFilter(){
    companion object{
        val logger = LoggerFactory.getLogger(HeaderFilter::class.java)
    }
    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        logger.info("HeaderFilter")
        val start = System.nanoTime()
        try {
            chain?.doFilter(request, response)
        } finally {
            val delta = System.nanoTime() - start
            val handler = request?.getAttribute(HeaderInterceptor.KEY) ?: "<unknown>"
            val debug = "Elapsed time was ${delta / 1000} us, handler=${handler}"
            logger.info(debug)
            response?.addHeader("Debug", debug)
        }
    }
}

class HeaderInterceptor : HandlerInterceptor{
    companion object{
        val logger = LoggerFactory.getLogger(HandlerInterceptor::class.java)
        val KEY = "HeaderInterceptor"
    }
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info("HandlerInterceptor")
        if(handler is HandlerMethod) {
            request.setAttribute(KEY, handler.method.name)
        }
        return true
    }
}

@Configuration
class HeaderConfiguration : WebMvcConfigurer {
    companion object {
        val logger = LoggerFactory.getLogger(HeaderConfiguration::class.java)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        logger.info("HeaderConfiguration")
        registry.addInterceptor(HeaderInterceptor())
    }
}*/