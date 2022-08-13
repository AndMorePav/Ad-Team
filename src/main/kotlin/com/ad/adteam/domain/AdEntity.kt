package com.ad.adteam.domain

import javax.persistence.*

@Entity
class AdEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_id_seq")
    var id: Long = 0,
    val title: String,
    val text: String,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val author: UserEntity
)
