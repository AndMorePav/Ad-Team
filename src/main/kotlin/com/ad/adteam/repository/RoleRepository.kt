package com.ad.adteam.repository

import com.ad.adteam.domain.RoleEntity
import com.ad.adteam.domain.URole
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<RoleEntity, Long> {
    fun findByName(name: URole): Optional<RoleEntity>
}