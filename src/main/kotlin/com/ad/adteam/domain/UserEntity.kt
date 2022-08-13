package com.ad.adteam.domain

import javax.persistence.*

@Entity
data class UserEntity(
        @Id
        @GeneratedValue
        var id: Long? = null,
        val login: String,
        val name: String,
        val surname: String,
        val age: Int,
        val phone: String,
        @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
        val ads: List<AdEntity>
)