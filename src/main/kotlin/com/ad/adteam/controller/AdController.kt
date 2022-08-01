package com.ad.adteam.controller

import com.ad.adteam.domain.AdEntity
import com.ad.adteam.service.AdService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ads")
class AdController(private val adService: AdService) {

    @GetMapping("/{userId}")
    fun getAdsByUser(@PathVariable userId: Long): List<AdEntity> = adService.getAdsByUser(userId)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAd(@RequestBody adEntity: AdEntity): AdEntity = adService.createAd(adEntity)

    @PatchMapping
    fun updateAd(@RequestBody adEntity: AdEntity) = adService.updateAd(adEntity)

    @DeleteMapping("/{adId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAd(@PathVariable adId: Long): Unit = adService.deleteAd(adId)
}