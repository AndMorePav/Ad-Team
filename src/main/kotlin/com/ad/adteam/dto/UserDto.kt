package com.ad.adteam.dto

import com.ad.adteam.domain.AdEntity

data class UserDto(
    val id: Long? = null,
    val login: String,
    val name: String,
    val surname: String,
    val age: Int,
    val phone: String,
    val ads: List<AdEntity> = emptyList()
)