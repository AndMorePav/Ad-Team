package com.ad.adteam.repository

import com.ad.adteam.domain.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, Long> {

}