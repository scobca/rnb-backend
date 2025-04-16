package org.itmo.rnbbackend.exceptions

import org.springframework.http.HttpStatus

abstract class BasicException(status: HttpStatus, message: String) : RuntimeException(message)