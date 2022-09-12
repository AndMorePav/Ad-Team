package com.ad.adteam.domain

import javax.persistence.*

@Entity
class RoleEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
        var id: Long = 0
        var name: URole? = null

        constructor()
        constructor(name: URole?) {
                this.name = name
        }
}