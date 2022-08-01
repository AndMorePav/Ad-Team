package com.ad.adteam.repository

import com.ad.adteam.domain.AdEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AdRepository: JpaRepository<AdEntity, Long> {
}