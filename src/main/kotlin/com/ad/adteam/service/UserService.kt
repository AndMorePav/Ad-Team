package com.ad.adteam.service

import com.ad.adteam.dto.UserDto

interface UserService {

    fun getUsers(): List<UserDto>

    fun getUser(userId: Long): UserDto

    fun createUser(userDto: UserDto): Long

    fun updateUser(userId: Long, userDto: UserDto): UserDto

    fun deleteUser(userId: Long)
}