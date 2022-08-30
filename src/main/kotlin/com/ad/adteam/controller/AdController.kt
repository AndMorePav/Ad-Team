package com.ad.adteam.controller

import com.ad.adteam.dto.AdDto
import com.ad.adteam.service.AdService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ads")
class AdController(private val adService: AdService) {

    @GetMapping
    fun searchAds(
        @RequestParam(defaultValue = "") textQuery: String,
        @RequestParam("page", defaultValue = "0") pageIndex: Int,
        @RequestParam("size", defaultValue = "10") pageSize: Int
    ): List<AdDto> = adService.searchAds(textQuery, pageIndex, pageSize)

    @GetMapping("/{userId}")
    fun getAdsByUser(
        @PathVariable userId: Long,
        @RequestParam(required = false) textQuery: String?,
        @RequestParam("page", defaultValue = "0") pageIndex: Int,
        @RequestParam("size", defaultValue = "10") pageSize: Int
    ): List<AdDto> = adService.getAdsByUser(userId, pageIndex, pageSize)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAd(@RequestBody adDto: AdDto): Long = adService.createAd(adDto)

    @PatchMapping
    fun updateAd(@RequestBody adDto: AdDto) = adService.updateAd(adDto)

    @DeleteMapping("/{adId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAd(@PathVariable adId: Long): Unit = adService.deleteAd(adId)
}