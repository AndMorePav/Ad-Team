package com.ad.adteam.service

import com.ad.adteam.domain.UserEntity
import com.ad.adteam.dto.UserDto
import com.ad.adteam.repository.AdRepository
import com.ad.adteam.repository.UserRepository
import com.ad.adteam.service.impl.UserServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito
import java.util.*

internal class UserServiceImplTest {

    private val userRepository = Mockito.mock(UserRepository::class.java)
    private val adRepository = Mockito.mock(AdRepository::class.java)
    private val userService = UserServiceImpl(adRepository, userRepository)
    private val userDto = UserDto(1, "test", "test", 18, "test", emptyList())
    private var userEntity = UserEntity()

    @BeforeEach
    internal fun setUp() {
        userEntity.id = 1
    }

    @Test
    fun `should update user entity`() {
        //given
        Mockito.`when`(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity))
        Mockito.`when`(userRepository.save(any(UserEntity::class.java))).thenReturn(userEntity)
        //when
        val updatedUser = userService.updateUser(1L, userDto)
        //then
        assertEquals(userDto, updatedUser)
    }

}