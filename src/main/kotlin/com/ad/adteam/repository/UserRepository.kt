package com.ad.adteam.repository

import com.ad.adteam.domain.UserEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, Long> {

    fun findByOrderByName(pageable: Pageable): List<UserEntity>

}