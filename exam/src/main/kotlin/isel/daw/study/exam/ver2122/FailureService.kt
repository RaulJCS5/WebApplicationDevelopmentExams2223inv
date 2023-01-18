package isel.daw.study.exam.ver2122

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.LinkedList
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@Service
class FailureService{
    private val failuresMap = LinkedList<FailureObject>()
    private val lock = ReentrantLock()
    fun addFailureObject(failureObject: FailureObject) {
        lock.withLock {
            if (failuresMap.size<10) {
                failuresMap.add(failureObject)
            }else{
                failuresMap.poll()
                failuresMap.add(failureObject)
            }
        }
    }
    fun getFailureObject():MutableList<FailureObject>{
        return failuresMap
    }
}

class FailureObject(
    val method : String,
    val uri : String,
    val status : Int,
    val controller : String,
    val methodController: String
)

@Component
class FailureFilter(
    val failureService: FailureService
):HttpFilter(){
    companion object {
        private val logger = LoggerFactory.getLogger(FailureFilter::class.java)
    }
    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        chain?.doFilter(request, response)
        val handler = request?.getAttribute(FailureInterceptor.KEY) as FailureObject
        failureService.addFailureObject(handler)
    }
}

class FailureInterceptor : HandlerInterceptor {
    companion object {
        private val logger = LoggerFactory.getLogger(FailureInterceptor::class.java)
        val KEY = "FailureInterceptor"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            request.setAttribute(KEY, FailureObject(
                request.method,
                request.requestURI,
                response.status,
                handler.method.name,
                handler.bean.toString()
            ))
        }
        return true
    }
}

@Configuration
class HandlerFailureInterceptor() : WebMvcConfigurer{
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(FailureInterceptor())
    }
}
