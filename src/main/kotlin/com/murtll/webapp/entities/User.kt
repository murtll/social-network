package com.murtll.webapp.entities

import javax.persistence.*

@Entity
@Table(name = "webapp.users")
data class User(
        @Id
        @Column(name = "username")
        var username: String? = null,
        @Column(name = "password")
        var password: String? = null,
        @Column(name = "enabled")
        var enabled: Boolean? = null
) {
        override fun equals(other: Any?): Boolean {
                return this.username == (other as User).username && this.enabled == other.enabled
        }

        override fun toString(): String {
                return "User [username=$username, password=$password, enabled=$enabled]"
        }

        override fun hashCode(): Int {
                var result = username?.hashCode() ?: 0
                result = 31 * result + (password?.hashCode() ?: 0)
                result = 31 * result + (enabled?.hashCode() ?: 0)
                return result
        }
}