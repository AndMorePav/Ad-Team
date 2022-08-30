package com.ad.adteam.service

import com.ad.adteam.dto.AdDto


interface AdService {
    fun searchAds(textQuery: String, pageIndex: Int, pageSize: Int): List<AdDto>
    fun getAdsByUser(userId: Long, pageIndex: Int, pageSize: Int): List<AdDto>
    fun createAd(adDto: AdDto): Long
    fun updateAd(adDto: AdDto): AdDto
    fun deleteAd(adId: Long)
}
