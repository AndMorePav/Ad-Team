package com.ad.adteam.service.impl

import com.ad.adteam.domain.UserEntity
import com.ad.adteam.dto.UserDto
import com.ad.adteam.exception.UserNotFoundException
import com.ad.adteam.repository.AdRepository
import com.ad.adteam.repository.UserRepository
import com.ad.adteam.service.UserService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val adRepository: AdRepository,
    private val userRepository: UserRepository
) : UserService {

    override fun getUsers(pageIndex: Int, pageSize: Int): List<UserDto> {
        return userRepository.findByOrderByName(PageRequest.of(pageIndex, pageSize))
            .map { it.toDto() }
    }

    override fun getUser(userId: Long): UserDto {
        return userRepository.findById(userId)
            .map { it.toDto() }
            .orElseThrow { UserNotFoundException(userId) }
    }

    @Transactional
    override fun updateUser(userId: Long, userDto: UserDto): UserDto {
        val foundedUser = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException(userId) }

        foundedUser.name = userDto.name
        foundedUser.surname = userDto.surname
        foundedUser.age = userDto.age
        foundedUser.phone = userDto.phone

        val savedUserEntity: UserEntity = userRepository.save(
            foundedUser
        )
        return savedUserEntity.toDto()
    }

    @Transactional
    override fun deleteUser(userId: Long) {
        val foundedUser = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException(userId) }
        adRepository.deleteByAuthor(foundedUser)
        userRepository.deleteById(userId)
    }

    private fun UserEntity.toDto(): UserDto =
        UserDto(
            id = this.id,
            name = this.name!!,
            surname = this.surname ?: "Undefined",
            age = this.age ?: 0,
            phone = this.phone!!,
            ads = this.ads
        )
}