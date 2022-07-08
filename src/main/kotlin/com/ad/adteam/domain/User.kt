package com.ad.adteam.domain

data class User(
        val id: Long,
        val login: String,
        val name: String,
        val surname: String,
        val age: Int,
        val phone: String,
)