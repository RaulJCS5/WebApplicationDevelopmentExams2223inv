package isel.daw.study.exam
import isel.daw.study.exam.MyHandlerInterceptor.Companion.KEY
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@RestController
class HandlerController(
    val handlerService:HandlerService
){

    @GetMapping("/handlers")
    fun getHandlersList():ResponseEntity<*>{
        return ResponseEntity
            .ok()
            .body(handlerService.getAllHandlers())
    }

    @GetMapping("/handlers/{handler-id}")
    fun getHandlers(@PathVariable("handler-id") parameter: String):ResponseEntity<*>{
        return ResponseEntity
            .ok()
            .body(handlerService.getHandler(handler_id = parameter))
    }

    @GetMapping("/map")
    fun getHandlersMap():ResponseEntity<*>{
        return ResponseEntity
            .ok()
            .body(handlerService.getHandlerMap())
    }

}
@Configuration
class HandlerConfiguration : WebMvcConfigurer{
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(MyHandlerInterceptor())
    }
}

class MyHandlerInterceptor : HandlerInterceptor {
    companion object{
        val KEY = "MyHandlerInterceptor"
    }
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod){
            request.setAttribute(KEY,handler.shortLogMessage)
        }
        return true
    }
}
@Component
class HandlerFilter(
    val handlerService: HandlerService
):HttpFilter(){
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        try {
            chain?.doFilter(request, response)
        }finally {
            val handler = request?.getAttribute(KEY)?:"<unknown>"
            handlerService.addHandler(handler as String)
        }
    }
}

@Service
class HandlerService {
    private val handlerMap = mutableMapOf<String,Int>()
    private val handlerList = mutableListOf<String>()
    private val lock = ReentrantLock()

    fun addHandler(handler: String) {
        lock.withLock {
            val getHandler = handlerMap[handler]
            if (getHandler==null){
                handlerList.add(handler)
                handlerMap[handler]=1
            }else{
                handlerMap[handler]=getHandler+1
            }
        }
    }

    fun getAllHandlers():MutableList<String>{
        return handlerList
    }

    fun getHandlerMap():MutableMap<String,Int>{
        return handlerMap
    }

    fun getHandler(handler_id : String): Int? {
        return handlerMap[handler_id]
    }

}
