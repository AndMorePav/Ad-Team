package com.ad.adteam.service

import com.ad.adteam.domain.UserEntity

interface UserService {

    fun getUsers(): List<UserEntity>

    fun getUser(userId: Long): UserEntity

    fun createUser(userEntity: UserEntity): UserEntity

    fun updateUser(userId: Long, userEntity: UserEntity): UserEntity

    fun deleteUser(userId: Long)
}