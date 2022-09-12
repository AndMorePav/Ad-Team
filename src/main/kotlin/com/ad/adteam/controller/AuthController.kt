package com.ad.adteam.controller

import com.ad.adteam.configuration.JwtUtil
import com.ad.adteam.domain.RoleEntity
import com.ad.adteam.domain.URole
import com.ad.adteam.domain.UserEntity
import com.ad.adteam.repository.RoleRepository
import com.ad.adteam.repository.UserRepository
import com.ad.adteam.security.request.LoginRequest
import com.ad.adteam.security.request.SignupRequest
import com.ad.adteam.security.response.JwtResponse
import com.ad.adteam.security.response.MessageResponse
import com.ad.adteam.service.UserDetailsImpl
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.GrantedAuthority
import java.util.stream.Collectors
import java.util.HashSet
import java.lang.RuntimeException
import java.util.function.Consumer

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val encoder: PasswordEncoder,
    private val jwtUtils: JwtUtil
) {

    @PostMapping("/signin")
    fun authenticateEmployee(@RequestBody loginRequest: @Valid LoginRequest?):
            ResponseEntity<*> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken
                (loginRequest!!.username, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl
        val roles =
            userDetails.authorities.stream().map { item: GrantedAuthority -> item.authority }
                .collect(Collectors.toList())
        return ResponseEntity.ok(
            JwtResponse(
                jwt, userDetails.id,
                userDetails.username,
                userDetails.phone!!, roles
            )
        )
    }

    @PostMapping("/signup")
    fun registerUser(
        @RequestBody signUpRequest:
        @Valid SignupRequest?
    ): ResponseEntity<*> {
        if (userRepository.existsByName(signUpRequest!!.username)!!) {
            return ResponseEntity.badRequest()
                .body(MessageResponse("Error: Employeename is already taken!"))
        }
        if (userRepository.existsByPhone(signUpRequest.phone)!!) {
            return ResponseEntity.badRequest().body(
                MessageResponse
                    ("Error: Email is already in use!")
            )
        }

        // Create new user account
        val user = UserEntity(
            signUpRequest.username,
            signUpRequest.phone,
            encoder.encode(signUpRequest.password)
        )
        val strRoles = signUpRequest.roles
        val roles: MutableSet<RoleEntity> = HashSet()
        if (strRoles == null) {
            val employeeRole = roleRepository.findByName(URole.ROLE_USER)
                .orElseThrow { RuntimeException("Error: Role is not found.") }!!
            roles.add(employeeRole)
        } else {
            strRoles.forEach(Consumer { role: String? ->
                when (role!!.lowercase()) {
                    "admin" -> {
                        val adminRole = roleRepository.findByName(URole.ROLE_ADMIN)
                            .orElseThrow { RuntimeException("Error: Role is not found.") }!!
                        roles.add(adminRole)
                    }
                    else -> {
                        val defaultRole = roleRepository.findByName(URole.ROLE_USER)
                            .orElseThrow { RuntimeException("Error: Role is not found.") }!!
                        roles.add(defaultRole)
                    }
                }
            })
        }
        user.roles = roles
        userRepository.save(user)
        return ResponseEntity.ok(MessageResponse("Employee registered successfully!"))
    }
}