package org.itmo.rnbbackend.exceptions

import org.springframework.http.HttpStatus

class NotFoundException(message: String) : BasicException(HttpStatus.NOT_FOUND, message)