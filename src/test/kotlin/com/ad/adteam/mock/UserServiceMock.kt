package com.ad.adteam.mock

import com.ad.adteam.domain.User
import com.ad.adteam.service.UserService

class UserServiceMock : UserService {

    private val users = listOf(User(1, "test", "test", "test", 18, "test"))
    override fun getUsers(): List<User> {
        TODO("Not yet implemented")
    }

    override fun getUser(userId: Long): User {
        TODO("Not yet implemented")
    }

    override fun createUser(user: User): User {
        TODO("Not yet implemented")
    }

    override fun updateUser(userId: Long, user: User): User {
        TODO("Not yet implemented")
    }

    override fun deleteUser(userId: Long) {
        TODO("Not yet implemented")
    }


}