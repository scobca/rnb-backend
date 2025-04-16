package org.itmo.rnbbackend.exceptions

import org.springframework.http.HttpStatus

class UnauthorizedException(message: String) : BasicException(HttpStatus.UNAUTHORIZED, message)