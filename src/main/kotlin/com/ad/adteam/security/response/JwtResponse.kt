package com.ad.adteam.security.response

class JwtResponse(
    var accessToken: String, var id: Long?,
    var username: String, var phone: String, val roles: List<String>
) {
    var tokenType = "Bearer"

}