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
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@RestController("/normal")
class HandlerController {

    @GetMapping("/handler")
    fun getHandler():ResponseEntity<*>{
        return ResponseEntity
            .accepted()
            .body("Get Handler")
    }
}

@Service
class HandlerService{

}

@Component
class HandlerFilter(
    val HandlerService: HandlerService
):HttpFilter(){
    companion object{
        val logger = LoggerFactory.getLogger(HandlerFilter::class.java)
    }
    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        logger.info("HandlerFilter")
        chain?.doFilter(request, response)
    }
}

class HandlerInterceptor : HandlerInterceptor{
    companion object{
        val logger = LoggerFactory.getLogger(HandlerInterceptor::class.java)
    }
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info("HandlerInterceptor")
        return true
    }
}

@Configuration
class HandlerConfiguration : WebMvcConfigurer {
    companion object{
        val logger = LoggerFactory.getLogger(HandlerConfiguration::class.java)
    }
    override fun addInterceptors(registry: InterceptorRegistry) {
        logger.info("HandlerConfiguration")
        registry.addInterceptor(HandlerInterceptor())
    }
}