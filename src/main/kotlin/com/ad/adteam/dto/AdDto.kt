package com.ad.adteam.dto

data class AdDto (
    var id: Long = 0,
    val title: String,
    val text: String,
    val authorId: Long
)