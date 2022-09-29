package com.ad.adteam.configuration

import com.ad.adteam.service.UserDetailsImpl
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*

@Component
class JwtUtil {

    @Value("\${app.jwtExpirationMs}")
    private val jwtExpirationMs = 0

    //todo ad exception messages when add logger
    @Value("\${app.jwtSecret}")
    private val jwtSecret: String? = null
    fun validateJwtToken(authToken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
        } catch (e: MalformedJwtException) {
        } catch (e: ExpiredJwtException) {
        } catch (e: UnsupportedJwtException) {
        } catch (e: IllegalArgumentException) {
        }
        return false
    }

    fun generateJwtToken(authentication: Authentication): String {
        val employeePrincipal = authentication.principal as UserDetailsImpl
        return Jwts.builder().setSubject(employeePrincipal.username).setIssuedAt(Date())
            .setExpiration(
                Date(
                    Date().time +
                            jwtExpirationMs
                )
            )
            .signWith(SignatureAlgorithm.HS512, jwtSecret).compact()
    }

    fun getUserNameFromJwtToken(token: String?): String {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject
    }
}