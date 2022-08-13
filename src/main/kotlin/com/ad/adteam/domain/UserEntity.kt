package com.ad.adteam.domain

import javax.persistence.*

@Entity
class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
        var id: Long = 0,
        val login: String,
        val name: String,
        val surname: String,
        val age: Int,
        val phone: String,
        @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
        var ads: List<AdEntity> = emptyList()
)