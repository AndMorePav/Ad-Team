package com.ad.adteam.service.impl

import com.ad.adteam.domain.AdEntity
import com.ad.adteam.repository.AdRepository
import com.ad.adteam.service.AdService
import org.springframework.stereotype.Service

@Service
class AdServiceImpl(private val adRepository: AdRepository) : AdService {

    override fun getAdsByUser(userId: Long): List<AdEntity> {
        TODO("Not yet implemented")
    }

    override fun createAd(adEntity: AdEntity): AdEntity {
        TODO("Not yet implemented")
    }

    override fun updateAd(adEntity: AdEntity): AdEntity {
        TODO("Not yet implemented")
    }

    override fun deleteAd(adId: Long) {
        TODO("Not yet implemented")
    }
}