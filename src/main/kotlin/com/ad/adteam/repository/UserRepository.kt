package com.ad.adteam.repository

import com.ad.adteam.domain.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository {

    fun getUsers(): List<User>

    fun getUser(userId: Long): User

    fun save(user: User): User

    fun deleteUser(userId: Long)
}