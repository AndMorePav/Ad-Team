package com.ad.adteam.domain

import javax.persistence.*

@Entity
class UserEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
        var id: Long = 0
        var name: String? = null
        var surname: String? = null
        var password: String? = null
        var age: Int? = null
        var phone: String? = null
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
                name = "user_roles",
                joinColumns = [JoinColumn(name = "role_id")],
                inverseJoinColumns = [JoinColumn(name = "user_id")]
        )
        var roles: Set<RoleEntity> = HashSet()
        @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
        val ads: List<AdEntity> = emptyList()

        constructor()
        constructor(name: String?, phone: String?, password: String?) : super() {
                this.name = name
                this.phone = phone
                this.password = password
        }
}