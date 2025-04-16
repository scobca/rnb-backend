package org.itmo.rnbbackend.controllers

import org.itmo.rnbbackend.dto.UpdateUserDto
import org.itmo.rnbbackend.dto.UserDto
import org.itmo.rnbbackend.entity.UserEntity
import org.itmo.rnbbackend.exceptions.NotFoundException
import org.itmo.rnbbackend.services.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {
    @GetMapping("/getAll")
    fun getAll() = userService.getAll()

    @GetMapping("/getById/{id}")
    fun getById(@PathVariable id: Long): UserEntity {
        val user = userService.getById(id)
        if (user == null) throw NotFoundException("User with id $id not found")

        return user
    }

    @GetMapping("/getByEmail/{email}")
    fun getByEmail(@PathVariable email: String): UserEntity {
        val user = userService.getByEmail(email)
        if (user == null) throw NotFoundException("User with email $email not found")

        return user
    }

    @PostMapping("/create")
    fun create(@RequestBody user: UserDto) = userService.create(user)

    @PatchMapping("/update")
    fun update(@RequestBody() updatedUser: UpdateUserDto) = userService.update(updatedUser)

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable id: Long) = userService.delete(id)
}