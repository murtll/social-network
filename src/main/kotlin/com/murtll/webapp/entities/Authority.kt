package com.murtll.webapp.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "webapp.authorities")
class Authority (
        @Id
        @Column(name = "username")
        val userName: String? = null,
        @Column(name = "authority")
        var authority: String? = null
)