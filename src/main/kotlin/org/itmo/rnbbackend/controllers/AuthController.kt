package org.itmo.rnbbackend.controllers

import org.itmo.rnbbackend.dto.LoginUserDto
import org.itmo.rnbbackend.dto.UserDto
import org.itmo.rnbbackend.services.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/register")
    fun register(@RequestBody data: UserDto) = authService.register(data)

    @PostMapping("/login")
    fun login(@RequestBody data: LoginUserDto) = authService.login(data)
}