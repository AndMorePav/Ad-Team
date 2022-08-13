package com.ad.adteam.domain

import javax.persistence.*

@Entity
data class AdEntity (
    @Id
    @GeneratedValue
    var id: Long? = null,
    val title: String,
    val text: String,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val author: UserEntity
)
