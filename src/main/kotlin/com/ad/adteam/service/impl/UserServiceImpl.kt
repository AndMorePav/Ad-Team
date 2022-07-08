package com.ad.adteam.service.impl

import com.ad.adteam.domain.User
import com.ad.adteam.repository.UserRepository
import com.ad.adteam.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository : UserRepository) : UserService {

    override fun getUsers(): List<User> = userRepository.getUsers()

    override fun getUser(userId: Long): User  = userRepository.getUser(userId)

    override fun createUser(user: User): User  = userRepository.save(user)

    override fun updateUser(userId: Long, user: User): User {
        return userRepository.getUser(userId)
    }

    override fun deleteUser(userId: Long)  = userRepository.deleteUser(userId)
}