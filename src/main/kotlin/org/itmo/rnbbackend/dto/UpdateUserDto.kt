package org.itmo.rnbbackend.dto

data class UpdateUserDto(
    val id: Long,
    val name: String?,
    val email: String?,
)