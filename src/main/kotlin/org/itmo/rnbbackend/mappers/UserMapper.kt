package org.itmo.rnbbackend.mappers

import org.itmo.rnbbackend.dto.UserDto
import org.itmo.rnbbackend.entity.UserEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    fun userEntityToDto(user: UserEntity): UserDto
    fun dtoToUserEntity(dto: UserDto): UserEntity
}