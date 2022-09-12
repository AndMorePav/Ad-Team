package com.ad.adteam.repository

import com.ad.adteam.domain.AdEntity
import com.ad.adteam.domain.UserEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface AdRepository: JpaRepository<AdEntity, Long>, JpaSpecificationExecutor<AdEntity> {
    fun findAllByAuthorIdOrderByTitle(userId: Long, pageable: Pageable): List<AdEntity>
    fun deleteByAuthor(userId: UserEntity)
}