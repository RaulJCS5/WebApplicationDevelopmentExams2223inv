package isel.daw.study.exam

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@RestController("/")
class PendingController(
    val pendingService: PendingService
) {
    private val pendingRequests = mutableMapOf<String, Int>()

    @GetMapping("/pending")
    fun pending():Map<String, Int>{
        return pendingService.getPending()
    }

    @GetMapping("/simplepending")
    fun getPendingRequests(request: HttpServletRequest): Map<String, Int> {
        val method = request.method
        pendingRequests[method] = pendingRequests.getOrDefault(method, 0) + 1
        return pendingRequests
    }
}

@Service
class PendingService{
    private val pendingRequests = mutableMapOf<String, Int>()
    private val lock = ReentrantLock()
    fun addPending(p:String){
        lock.withLock {
            pendingRequests[p] = pendingRequests.getOrDefault(p, 0) + 1
        }
    }
    fun getPending():MutableMap<String,Int>{
        return pendingRequests
    }
}