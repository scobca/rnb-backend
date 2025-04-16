package org.itmo.rnbbackend.services

import jakarta.transaction.Transactional
import org.itmo.rnbbackend.dto.UpdateUserDto
import org.itmo.rnbbackend.dto.UserAuthDto
import org.itmo.rnbbackend.dto.UserDto
import org.itmo.rnbbackend.entity.UserEntity
import org.itmo.rnbbackend.exceptions.DoubleRecordException
import org.itmo.rnbbackend.exceptions.NotFoundException
import org.itmo.rnbbackend.io.BasicHttpResponse
import org.itmo.rnbbackend.mappers.UserMapper
import org.itmo.rnbbackend.repositories.UserRepository
import org.itmo.rnbbackend.utils.JwtUtil
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtUtil: JwtUtil,
) {
    fun getAll(): List<UserEntity> = userRepository.findAll()
    fun getById(id: Long): UserEntity? = userRepository.findById(id).orElse(null)
    fun getByEmail(email: String): UserEntity? = userRepository.findByEmail(email)

    @Transactional
    fun create(dto: UserDto): BasicHttpResponse<UserAuthDto> {
        val existingUser = getByEmail(dto.email)
        if (existingUser != null) throw DoubleRecordException("User with email ${dto.email} already exists")

        dto::password.set(passwordEncoder.encode(dto.password))
        val newUser = userMapper.dtoToUserEntity(dto)

        userRepository.save<UserEntity>(newUser)

        val user = getByEmail(dto.email)
        return BasicHttpResponse<UserAuthDto>(HttpStatus.OK, UserAuthDto(jwtUtil.generateToken(user!!)))
    }

    @Transactional
    fun update(dto: UpdateUserDto): BasicHttpResponse<String> {
        val user = getById(dto.id)
        if (user == null) throw NotFoundException("User with id ${dto.id} not found")

        if (dto.email != null) {
            val emailUsage = getByEmail(dto.email)
            if (emailUsage != null) throw DoubleRecordException("User with email ${dto.email} already exists")
        }

        dto.name?.let { user::username.set(it) }
        dto.email?.let { user::email.set(it) }

        return BasicHttpResponse(HttpStatus.OK, "User updated successfully")
    }

    @Transactional
    fun delete(id: Long): BasicHttpResponse<String> {
        val user = getById(id)
        if (user == null) throw NotFoundException("User with id $id not found")

        userRepository.delete(user)
        return BasicHttpResponse(HttpStatus.OK, "User deleted successfully")
    }
}