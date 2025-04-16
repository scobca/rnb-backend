package org.itmo.rnbbackend.exceptions.handlers

import org.itmo.rnbbackend.exceptions.DoubleRecordException
import org.itmo.rnbbackend.exceptions.NotFoundException
import org.itmo.rnbbackend.exceptions.UnauthorizedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BasicExceptionHandler {
    @ExceptionHandler
    fun handlerNotFoundException(e: UnauthorizedException): ResponseEntity<ErrorMessage?> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorMessage(
                statusCode = HttpStatus.BAD_REQUEST.value(),
                errorName = e.javaClass.simpleName,
                message = e.message ?: "Message not provided"
            )
        )
    }

    @ExceptionHandler
    fun handlerNotFoundException(e: NotFoundException): ResponseEntity<ErrorMessage?> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorMessage(
                statusCode = HttpStatus.NOT_FOUND.value(),
                errorName = e.javaClass.simpleName,
                message = e.message ?: "Message not provided"
            )
        )
    }

    @ExceptionHandler
    fun handleDoubleRecordException(e: DoubleRecordException): ResponseEntity<ErrorMessage> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ErrorMessage(
                statusCode = HttpStatus.CONFLICT.value(),
                errorName = e.javaClass.simpleName,
                message = e.message ?: "Message not provided"
            )
        )
    }
}