package isel.daw.study.exam.ver2122

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController("/")
class Controller(
    val pendingService: PendingService,
    val statusService: StatusService,
    val demoService: DemoService
) {
    @GetMapping("/demo")
    fun getFailures(): ResponseEntity<*> {
        return ResponseEntity
            .accepted()
            .body(demoService.getDemoObject())
    }

    @GetMapping("/status/{method}")
    fun getStatus(@PathVariable method: String): ResponseEntity<*> {
        return statusService.getStatusMethod("/$method")
    }

    @GetMapping("/status1")
    fun getStatus1(servletRequest: HttpServletRequest): String {
        return "Get status 1: ${servletRequest.requestURI}"
    }

    @PostMapping("/status2")
    fun getStatus2(servletRequest: HttpServletRequest): String {
        return "Get status 2: ${servletRequest.requestURI}"
    }

    @PutMapping("/status3")
    fun getStatus3(servletRequest: HttpServletRequest): String {
        return "Get status 3: ${servletRequest.requestURI}"
    }

    private val pendingRequests = mutableMapOf<String, Int>()

    @GetMapping("/pending")
    fun pending(): Map<String, Int> {
        return pendingService.getPending()
    }

    @GetMapping("/simplepending")
    fun getPendingRequests(request: HttpServletRequest): Map<String, Int> {
        val method = request.method
        pendingRequests[method] = pendingRequests.getOrDefault(method, 0) + 1
        return pendingRequests
    }
}