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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@RestController("/recurso")
class AnonymousController(
    val anonymousService: AnonymousService
) {

    @GetMapping("/anonymous")
    fun getAnonymous():ResponseEntity<*>{
        return ResponseEntity
            .accepted()
            .body(anonymousService.getAnonymous())
    }
}

@Service
class AnonymousService{
    private val mapAnonymous= mutableMapOf<String,Int>()
    fun addAnonymous(uri:String){
        val mapUri = mapAnonymous[uri]
        if (mapUri!=null) {
            mapAnonymous[uri] = mapUri + 1
        }else{
            mapAnonymous[uri]=1
        }
    }
    fun getAnonymous():MutableMap<String, Int>{
        return mapAnonymous
    }
}

@Component
class AnonymousFilter(
    val anonymousService: AnonymousService
):HttpFilter(){
    companion object{
        val logger = LoggerFactory.getLogger(AnonymousFilter::class.java)
    }
    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        logger.info("${request?.authType}")
        if (request!=null&&request.requestURI!="/anonymous"&&request.authType==null) {
            anonymousService.addAnonymous(request.requestURI)
        }
        chain?.doFilter(request, response)
    }
}

class AnonymousInterceptor : HandlerInterceptor{
    companion object{
        val logger = LoggerFactory.getLogger(AnonymousInterceptor::class.java)
    }
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info("AnonymousInterceptor")
        return true
    }
}

@Configuration
class AnonymousConfiguration : WebMvcConfigurer {
    companion object{
        val logger = LoggerFactory.getLogger(AnonymousConfiguration::class.java)
    }
    override fun addInterceptors(registry: InterceptorRegistry) {
        logger.info("AnonymousConfiguration")
        registry.addInterceptor(AnonymousInterceptor())
    }
}