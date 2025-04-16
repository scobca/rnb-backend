package org.itmo.rnbbackend.io

import org.springframework.http.HttpStatus

data class BasicHttpResponse<T>(
    val status: HttpStatus,
    val message: T,
)