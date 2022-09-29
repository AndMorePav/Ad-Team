package com.ad.adteam.service

import com.ad.adteam.domain.RoleEntity
import com.ad.adteam.domain.UserEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

class UserDetailsImpl(
    val id: Long?, private val username: String, val phone: String?,
    @field:JsonIgnore private val password: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {
    /**
     * Returns the authorities granted to the user. Cannot return `null`.
     * @return the authorities, sorted by natural key (never `null`)
     */
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities;
    }

    /**
     * Returns the password used to authenticate the user.
     * @return the password
     */
    override fun getPassword(): String {
        return password
    }

    /**
     * Returns the username used to authenticate the user. Cannot return
     * `null`.
     * @return the username (never `null`)
     */
    override fun getUsername(): String {
        return username
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     * @return `true` if the user's account is valid (ie non-expired),
     * `false` if no longer valid (ie expired)
     */
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     * @return `true` if the user is not locked, `false` otherwise
     */
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     * @return `true` if the user's credentials are valid (ie non-expired),
     * `false` if no longer valid (ie expired)
     */
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     * @return `true` if the user is enabled, `false` otherwise
     */
    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as UserDetailsImpl
        return id == user.id
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + password.hashCode()
        result = 31 * result + authorities.hashCode()
        return result
    }

    companion object {
        private const val serialVersionUID = 1L
        fun build(user: UserEntity): UserDetailsImpl {
            val authorities = user.roles.stream()
                .map { role: RoleEntity ->
                    SimpleGrantedAuthority(
                        role.name!!.name
                    )
                }.collect(Collectors.toList())
            return UserDetailsImpl(
                user.id, user.name!!,
                user.phone, user.password!!,
                authorities
            )
        }
    }
}