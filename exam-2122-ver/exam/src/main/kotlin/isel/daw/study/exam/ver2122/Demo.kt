package isel.daw.study.exam.ver2122

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@Service
class DemoService{
    private val demoMap = mutableMapOf<String,DemoObject>()
    private val lock = ReentrantLock()
    fun addDemoObject(requestURI: String, demoObject: DemoObject) {
        lock.withLock {
            demoMap[requestURI]=demoObject
        }
    }

    fun getDemoObject():MutableMap<String,DemoObject>{
        return demoMap
    }

}

class DemoObject(
    val value1 : String,
    val value2 : Long,
    val value3 : Int,
)

@Component
class DemoFilter(
    val demoService: DemoService
):HttpFilter(){
    companion object {
        private val logger = LoggerFactory.getLogger(DemoFilter::class.java)
    }
    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        val start = System.nanoTime()
        logger.info("DemoFilter before")

        chain?.doFilter(request, response)

        val end = System.nanoTime()-start
        logger.info("DemoFilter after $end")
        if (request!=null&&request.requestURI!="/demo"&&response!=null) {
            demoService.addDemoObject(request.requestURI, DemoObject(request.method, end, response.status))
        }
    }
}

class DemoInterceptor : HandlerInterceptor{
    companion object {
        private val logger = LoggerFactory.getLogger(DemoInterceptor::class.java)
    }
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.info("DemoInterceptor")
        return true
    }
}

@Configuration
class HandlerDemoInterceptor() : WebMvcConfigurer{
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(DemoInterceptor())
    }
}
