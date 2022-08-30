package com.ad.adteam.repository

import com.ad.adteam.domain.AdEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface AdRepository: JpaRepository<AdEntity, Long>, JpaSpecificationExecutor<AdEntity> {
    fun findAllByAuthorIdOrderByTitle(userId: Long, pageable: Pageable): List<AdEntity>
}