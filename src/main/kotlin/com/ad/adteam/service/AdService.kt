package com.ad.adteam.service

import com.ad.adteam.domain.AdEntity

interface AdService {

    fun getAdsByUser(userId: Long): List<AdEntity>
    fun createAd(adEntity: AdEntity): AdEntity
    fun updateAd(adEntity: AdEntity): AdEntity
    fun deleteAd(adId: Long)

}
