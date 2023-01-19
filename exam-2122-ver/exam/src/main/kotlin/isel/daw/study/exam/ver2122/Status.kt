package isel.daw.study.exam.ver2122

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@Service
class StatusService {
    private val mapStatus = mutableMapOf<String, MaxMin>()
    private val lock = ReentrantLock()
    fun addStatusMethod(method: String, time: Long) {
        lock.withLock {
            val maxMin = mapStatus[method]
            if (maxMin == null) {
                mapStatus[method] = MaxMin(time, time)
            } else {
                if (time > maxMin.max) {
                    val newMaxMin = MaxMin(time, maxMin.min)
                    mapStatus[method] = newMaxMin
                } else if (time < maxMin.min) {
                    val newMaxMin = MaxMin(maxMin.max, time)
                    mapStatus[method] = newMaxMin
                }

            }
        }
    }

    fun getStatusMethod(method: String): ResponseEntity<*> {
        val methodStatus = mapStatus[method]
        if (methodStatus != null) {
            return ResponseEntity
                .status(200)
                .body(ResponseMaxMin(method,methodStatus))
        }
        return ResponseEntity
            .status(404)
            .body(null)
    }
}
/*
@Component
class StatusFilter(
    val statusService: StatusService
): HttpFilter()
{
    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        val start = System.nanoTime()
        try {
            chain?.doFilter(request, response)
        }
        finally {
            val end = System.nanoTime()-start
            val method = request?.requestURI
            if (method != null) {
                statusService.addStatusMethod(method,end)
            }
        }
    }
}
*/
class MaxMin(
    val max:Long,
    val min:Long
)

class ResponseMaxMin(
    val method:String,
    val maxMin:MaxMin
)