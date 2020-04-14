package com.murtll.webapp.entities

import javax.persistence.*

@Entity
@Table(name = "webapp.messages")
class Message(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: Long? = null,
        @Column(name = "message")
        var messageText: String? = null,
        @Column(name = "from_user")
        var fromUser: String? = null,
        @Column(name = "to_user")
        var toUser: String? = null,
        @Column(name = "time")
        var time: Int? = null,
        @Column(name = "date")
        var date: Int? = null
)