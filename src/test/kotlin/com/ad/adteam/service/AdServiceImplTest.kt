package com.ad.adteam.service

import com.ad.adteam.domain.AdEntity
import com.ad.adteam.domain.UserEntity
import com.ad.adteam.dto.AdDto
import com.ad.adteam.dto.UserDto
import com.ad.adteam.repository.AdRepository
import com.ad.adteam.repository.UserRepository
import com.ad.adteam.service.impl.AdServiceImpl
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito
import java.util.*

internal class AdServiceImplTest {

    private val adRepository = Mockito.mock(AdRepository::class.java)
    private val userRepository = Mockito.mock(UserRepository::class.java)
    private val adService = AdServiceImpl(adRepository, userRepository)
    private val updatedAdDto = AdDto(1, "Test Title", "Test text", 1L)
    private val userEntity = UserEntity(1, "test", "test", "test", 18, "test", emptyList())
    private val adEntity = AdEntity(1, "Test Title", "Test text", userEntity)

    @Test
    fun `should update ad entity`() {
        //given'
        Mockito.`when`(adRepository.findById(anyLong())).thenReturn(Optional.of(adEntity))
        Mockito.`when`(adRepository.save(any(AdEntity::class.java))).thenReturn(adEntity)
        //when
        val updateAd = adService.updateAd(updatedAdDto)
        //then
        assertEquals(updatedAdDto, updateAd)
    }
}