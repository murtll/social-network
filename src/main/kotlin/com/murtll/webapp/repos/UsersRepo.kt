package com.murtll.webapp.repos

import com.murtll.webapp.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface UsersRepo : JpaRepository<User, String> {
    fun findOneByUsername(username: String): User
    @Transactional
    fun deleteByUsername(username: String)
}