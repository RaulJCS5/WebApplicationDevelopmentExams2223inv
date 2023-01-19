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

@RestController("/")
class HeaderController {

    @GetMapping("/header")
    fun getHeader():ResponseEntity<*>{
        return ResponseEntity
            .accepted()
            .body("Get header")
    }
}

@Service
class HeaderService{

}

@Component
class HeaderFilter(
    val headerService: HeaderService
):HttpFilter(){
    companion object{
        val logger = LoggerFactory.getLogger(HeaderFilter::class.java)
    }
    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        logger.info("HeaderFilter")
        chain?.doFilter(request, response)
    }
}

class HeaderInterceptor : HandlerInterceptor{
    companion object{
        val logger = LoggerFactory.getLogger(HandlerInterceptor::class.java)
    }
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info("HandlerInterceptor")
        return true
    }
}

@Configuration
class HeaderConfiguration : WebMvcConfigurer {
    companion object{
        val logger = LoggerFactory.getLogger(HeaderConfiguration::class.java)
    }
    override fun addInterceptors(registry: InterceptorRegistry) {
        logger.info("HeaderConfiguration")
        registry.addInterceptor(HeaderInterceptor())
    }
}