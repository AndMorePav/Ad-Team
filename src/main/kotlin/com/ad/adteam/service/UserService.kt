package com.ad.adteam.service

import com.ad.adteam.domain.User

interface UserService {

    fun getUsers(): List<User>

    fun getUser(userId: Long): User

    fun createUser(user: User): User

    fun updateUser(userId: Long, user: User): User

    fun deleteUser(userId: Long)
}