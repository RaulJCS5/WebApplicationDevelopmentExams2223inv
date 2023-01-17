package isel.daw.study.exam

import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@RestController("/")
class PendingController(
    val pendingService: PendingService
) {
    @GetMapping("/pending")
    fun pending():String{
        return pendingService.getPending().toString()
    }
}

@Service
class PendingService{
    private val list = mutableListOf<String>()
    private val lock = ReentrantLock()
    fun addPending(p:String){
        lock.withLock {
            list.add(p)
        }
    }
    fun getPending():MutableList<String>{
        return list
    }
}