package com.murtll.webapp.repos

import com.murtll.webapp.entities.Authority
import com.murtll.webapp.entities.FriendItem
import com.murtll.webapp.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface AuthoritiesRepo : JpaRepository <Authority, String> {
    @Transactional
    fun deleteByUserName(username: String)
}