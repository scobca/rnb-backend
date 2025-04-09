package org.itmo.rnbbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RnbBackendApplication

fun main(args: Array<String>) {
    runApplication<RnbBackendApplication>(*args)
}
