package com.ad.adteam.repository

import com.ad.adteam.domain.AdEntity
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AdRepository: JpaRepository<AdEntity, Long> {
    fun findAllByAuthorIdOrderByTitle(userId: Long, pageable: Pageable): List<AdEntity>
}