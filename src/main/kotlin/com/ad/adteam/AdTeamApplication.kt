package com.ad.adteam

import com.ad.adteam.domain.RoleEntity
import com.ad.adteam.domain.URole
import com.ad.adteam.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AdTeamApplication : CommandLineRunner {

    @Autowired
    var roleRepository: RoleRepository? = null

    /**
     * Callback used to run the bean.
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    override fun run(vararg args: String) {
        try {
            val role = RoleEntity(URole.ROLE_USER)
            roleRepository!!.save<RoleEntity>(role)
            val role2 = RoleEntity(URole.ROLE_ADMIN)
            roleRepository!!.save<RoleEntity>(role2)
        } catch (e: Exception) {
        }
    }
}

fun main(args: Array<String>) {
    runApplication<AdTeamApplication>(*args)
}
