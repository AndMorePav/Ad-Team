package com.ad.adteam.security.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class SignupRequest {
    var username: @NotBlank @Size(min = 3, max = 20) String? = null
    //fixme phone pattern
    var phone: @NotBlank @Size(max = 50) String? = null
    var roles: Set<String>? = null
        private set
    var password: @NotBlank @Size(min = 6, max = 40) String? = null
    fun setRoles(roles: Set<String>?) {
        this.roles = roles
    }
}