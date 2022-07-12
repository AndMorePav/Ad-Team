package com.ad.adteam.exception

import org.springframework.http.HttpStatus

class UserNotFoundException (userId: Long): BaseException(
    HttpStatus.NOT_FOUND,
    ApiError(
        errorCode = "user.not.found",
        description = "User not found with id=$userId"
    )
)