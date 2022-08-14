package com.ad.adteam.service.impl

import com.ad.adteam.domain.AdEntity
import com.ad.adteam.dto.AdDto
import com.ad.adteam.exception.AdNotFoundException
import com.ad.adteam.exception.UserNotFoundException
import com.ad.adteam.repository.AdRepository
import com.ad.adteam.repository.UserRepository
import com.ad.adteam.service.AdService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdServiceImpl(
    private val adRepository: AdRepository,
    private val userRepository: UserRepository
) : AdService {

    override fun getAdsByUser(userId: Long, pageIndex: Int, pageSize: Int): List<AdDto> {
        return adRepository.findAllByAuthorIdOrderByTitle(
            userId, PageRequest.of(pageIndex, pageSize)
        ).map { it.toDto() }
    }

    override fun createAd(adDto: AdDto): Long {
        return adRepository.save(adDto.toEntity()).id!!
    }

    @Transactional
    override fun updateAd(adDto: AdDto): AdDto {
        val foundedAd = adRepository.findById(adDto.id)
            .orElseThrow { AdNotFoundException(adDto.id) }

        val savedAd = adRepository.save(
            AdEntity(
                adDto.id,
                adDto.title,
                adDto.text,
                foundedAd.author
            )
        )
        return savedAd.toDto()
    }

    override fun deleteAd(adId: Long) {
        adRepository.deleteById(adId)
    }

    private fun AdDto.toEntity(): AdEntity =
        AdEntity(
            id = 0,
            title = this.title,
            text = this.text,
            author = userRepository.findById(this.authorId)
                .orElseThrow { UserNotFoundException(this.authorId) }
        )

    private fun AdEntity.toDto(): AdDto =
        AdDto(
            id = this.id,
            title = this.title,
            text = this.text,
            authorId = this.author.id

        )
}