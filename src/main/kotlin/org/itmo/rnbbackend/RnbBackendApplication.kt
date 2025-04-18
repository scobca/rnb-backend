package org.itmo.rnbbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class RnbBackendApplication

fun main(args: Array<String>) {
    runApplication<RnbBackendApplication>(*args)
}
