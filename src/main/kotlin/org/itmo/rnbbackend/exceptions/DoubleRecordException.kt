package org.itmo.rnbbackend.exceptions

import org.springframework.http.HttpStatus


class DoubleRecordException(message: String) : BasicException(HttpStatus.CONFLICT, message)