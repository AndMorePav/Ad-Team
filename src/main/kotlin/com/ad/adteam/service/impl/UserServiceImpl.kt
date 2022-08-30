package com.ad.adteam.service.impl

import com.ad.adteam.domain.UserEntity
import com.ad.adteam.dto.UserDto
import com.ad.adteam.exception.UserNotFoundException
import com.ad.adteam.repository.UserRepository
import com.ad.adteam.service.UserService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun getUsers(pageIndex: Int, pageSize: Int): List<UserDto>
    = userRepository.findByOrderByName(PageRequest.of(pageIndex, pageSize)).map { it.toDto() }

    override fun getUser(userId: Long): UserDto {
        return userRepository.findById(userId)
            .map { it.toDto() }
            .orElseThrow { UserNotFoundException(userId) }
    }

    override fun createUser(userDto: UserDto): Long {
        val savedUser = userRepository.save(userDto.toEntity())
        return savedUser.id
    }

    @Transactional
    override fun updateUser(userId: Long, userDto: UserDto): UserDto {
        val foundedUser = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException(userId) }

        val savedUserEntity: UserEntity = userRepository.save(
            UserEntity(
                foundedUser.id,
                userDto.login,
                userDto.name,
                userDto.surname,
                userDto.age,
                userDto.phone
            )
        )
        return savedUserEntity.toDto()
    }

    override fun deleteUser(userId: Long) = userRepository.deleteById(userId)

    private fun UserDto.toEntity(): UserEntity =
        UserEntity(
            id = 0,
            login = this.login,
            name = this.name,
            surname = this.surname,
            age = this.age,
            phone = this.phone
        )

    private fun UserEntity.toDto(): UserDto =
        UserDto(
            id = this.id,
            login = this.login,
            name = this.name,
            surname = this.surname,
            age = this.age,
            phone = this.phone,
            ads = this.ads
        )
}