package com.ad.adteam.controller

import com.ad.adteam.dto.AdDto
import com.ad.adteam.service.AdService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ads")
class AdController(private val adService: AdService) {

    @GetMapping("/{userId}")
    fun getAdsByUser(@PathVariable userId: Long): List<AdDto> = adService.getAdsByUser(userId)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAd(@RequestBody adDto: AdDto): Long = adService.createAd(adDto)

    @PatchMapping
    fun updateAd(@RequestBody adDto: AdDto) = adService.updateAd(adDto)

    @DeleteMapping("/{adId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAd(@PathVariable adId: Long): Unit = adService.deleteAd(adId)
}