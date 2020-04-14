package com.murtll.webapp.entities

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "webapp.friend_items")
class FriendItem (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @ManyToOne
        @JoinColumn(name = "username")
        var mainUser: User? = null,

        @ManyToOne
        @JoinColumn(name = "friendname")
        val friendUser: User? = null
) : Serializable {

        override fun equals(other: Any?): Boolean {
                return (this.friendUser == (other as FriendItem).friendUser && this.mainUser == other.mainUser) || (this.friendUser == (other).mainUser && this.friendUser == other.mainUser)
        }

        override fun hashCode(): Int {
                var result = mainUser?.hashCode() ?: 0
                result = 31 * result + (friendUser?.hashCode() ?: 0)
                return result
        }
}