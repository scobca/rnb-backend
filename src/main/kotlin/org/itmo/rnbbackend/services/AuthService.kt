package org.itmo.rnbbackend.services

import jakarta.transaction.Transactional
import org.itmo.rnbbackend.dto.LoginUserDto
import org.itmo.rnbbackend.dto.UserAuthDto
import org.itmo.rnbbackend.dto.UserDto
import org.itmo.rnbbackend.exceptions.DoubleRecordException
import org.itmo.rnbbackend.exceptions.UnauthorizedException
import org.itmo.rnbbackend.io.BasicHttpResponse
import org.itmo.rnbbackend.utils.JwtUtil
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserService,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtUtil: JwtUtil,
) {
    @Transactional
    fun register(user: UserDto): BasicHttpResponse<UserAuthDto> {
        val currentUser = userService.getByEmail(user.email)
        if (currentUser != null) throw DoubleRecordException("User with this email already registered")

        return userService.create(user)
    }

    fun login(data: LoginUserDto): BasicHttpResponse<UserAuthDto> {
        val user = userService.getByEmail(data.email)
        if (user == null) throw UnauthorizedException("Wrong email or password.")

        if (passwordEncoder.matches(data.password, user.password)) {
            return BasicHttpResponse<UserAuthDto>(HttpStatus.OK, UserAuthDto(jwtUtil.generateToken(user)))
        } else {
            throw UnauthorizedException("Wrong email or password.")
        }
    }
}