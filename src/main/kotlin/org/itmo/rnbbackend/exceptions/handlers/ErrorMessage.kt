package org.itmo.rnbbackend.exceptions.handlers

import kotlinx.serialization.Serializable

@Serializable
data class ErrorMessage(
    private val statusCode: Int,
    private val errorName: String,
    private val message: String,
)