package com.ad.adteam.service.impl

import com.ad.adteam.domain.User
import com.ad.adteam.exception.UserNotFoundException
import com.ad.adteam.repository.UserRepository
import com.ad.adteam.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun getUsers(): List<User> = userRepository.findAll()

    override fun getUser(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow { UserNotFoundException(userId) }
    }

    override fun createUser(user: User): User = userRepository.save(user)

    override fun updateUser(userId: Long, user: User): User {
        return userRepository.findById(userId)
            .orElseThrow { UserNotFoundException(userId) }
    }

    override fun deleteUser(userId: Long) = userRepository.deleteById(userId)
}