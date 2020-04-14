package com.murtll.webapp.repos

import com.murtll.webapp.entities.FriendItem
import com.murtll.webapp.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface FriendsRepo : JpaRepository <FriendItem, Long> {
    fun findAllByMainUser(user: User): List<FriendItem>
    @Transactional
    fun deleteByMainUserAndFriendUser(user: User, friend: User)
    @Transactional
    fun deleteAllByFriendUserOrMainUser(user: User, friend: User)
}