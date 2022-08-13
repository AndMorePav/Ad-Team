package com.ad.adteam.service.impl

import com.ad.adteam.domain.UserEntity
import com.ad.adteam.exception.UserNotFoundException
import com.ad.adteam.repository.UserRepository
import com.ad.adteam.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun getUsers(): List<UserEntity> = userRepository.findAll()

    override fun getUser(userId: Long): UserEntity {
        return userRepository.findById(userId)
            .orElseThrow { UserNotFoundException(userId) }
    }

    override fun createUser(userEntity: UserEntity): UserEntity = userRepository.save(userEntity)

    override fun updateUser(userId: Long, userEntity: UserEntity): UserEntity {
        return userRepository.findById(userId)
            .orElseThrow { UserNotFoundException(userId) }
    }

    override fun deleteUser(userId: Long) = userRepository.deleteById(userId)
}