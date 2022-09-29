package com.ad.adteam.repository

import com.ad.adteam.domain.UserEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<UserEntity, Long> {

    fun findByOrderByName(pageable: Pageable): List<UserEntity>
    fun findByName(name: String) : Optional<UserEntity>
    fun existsByName(name: String?): Boolean?
    fun existsByPhone(phone: String?): Boolean?
}