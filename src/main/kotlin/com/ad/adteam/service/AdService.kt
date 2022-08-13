package com.ad.adteam.service

import com.ad.adteam.dto.AdDto


interface AdService {

    fun getAdsByUser(userId: Long): List<AdDto>
    fun createAd(adDto: AdDto): Long
    fun updateAd(adDto: AdDto): AdDto
    fun deleteAd(adId: Long)

}
