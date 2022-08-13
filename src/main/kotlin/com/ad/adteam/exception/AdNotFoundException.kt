package com.ad.adteam.exception

import org.springframework.http.HttpStatus

class AdNotFoundException(adId: Long) : BaseException(
    HttpStatus.NOT_FOUND,
    ApiError(
        errorCode = "ad.not.found",
        description = "Ad not found with id=$adId"
    )
)
