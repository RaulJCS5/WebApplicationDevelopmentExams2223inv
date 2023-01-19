package isel.daw.study.exam

import isel.daw.study.exam.HandlerInterceptor.Companion.KEY
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
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@RestController("/normal")
class HandlerController(
    val handlerService: HandlerService
) {
    @GetMapping("/handlers")
    fun getHandler():ResponseEntity<*>{
        return ResponseEntity
            .accepted()
            .body(handlerService.getHandler())
    }
    @GetMapping("/handlerslist")
    fun getHandler1():ResponseEntity<*>{
        return ResponseEntity
            .accepted()
            .body(handlerService.getHandlerList())
    }
    @GetMapping("/demo")
    fun getDemo():ResponseEntity<*>{
        return ResponseEntity
            .accepted()
            .body("For demo only")
    }
}
@Service
class HandlerService{
    val mapHandler = mutableMapOf<String,MutableList<Long>>()
    val mapUseHandler = mutableMapOf<String,UseHandler>()
    val lock = ReentrantLock()
    fun addHandler(requestHandler: String, time:Long) {
        lock.withLock {
            if (mapHandler[requestHandler]==null){
                val listHandler = mutableListOf<Long>()
                listHandler.add(time)
                mapHandler[requestHandler]=listHandler
                val mapHandlerMedia = mapHandler[requestHandler]
                if (mapHandlerMedia!=null) {
                    mapUseHandler[requestHandler] = UseHandler(mapHandlerMedia.size, listHandler[0])
                }
            }else{
                mapHandler[requestHandler]?.add(time)
                var media : Long =0
                mapHandler[requestHandler]?.map {
                    media+=it
                }
                val mapHandlerMedia = mapHandler[requestHandler]
                if (mapHandlerMedia!=null) {
                    mapUseHandler[requestHandler] = UseHandler(mapHandlerMedia.size, media / mapHandlerMedia.size)
                }
            }
        }
    }
    fun getHandler(): MutableMap<String,UseHandler> {
        return mapUseHandler
    }
    fun getHandlerList(): MutableMap<String,MutableList<Long>> {
        return mapHandler
    }

}
class UseHandler(
    val count:Int,
    val time:Long
)
@Component
class HandlerFilter(
    val handlerService: HandlerService
):HttpFilter(){
    companion object{
        val logger = LoggerFactory.getLogger(HandlerFilter::class.java)
    }
    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        logger.info("HandlerFilter")
        val start = System.nanoTime()
        chain?.doFilter(request, response)
        val end = System.nanoTime() - start
        if (request!=null&&request.requestURI!="/handler") {
            val getLogMessage = request.getAttribute(KEY)
            handlerService.addHandler(getLogMessage as String, end)
        }
    }
}
class HandlerInterceptor : HandlerInterceptor{
    companion object{
        val KEY = "HandlerInterceptor"
    }
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod){
            request.setAttribute(KEY,handler.shortLogMessage)
        }
        return true
    }
}
@Configuration
class HandlerConfiguration : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(HandlerInterceptor())
    }
}