package com.murtll.webapp.repos

import com.murtll.webapp.entities.FriendItem
import com.murtll.webapp.entities.Message
import com.murtll.webapp.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface MessagesRepo : JpaRepository <Message, Long> {
    fun deleteAllByFromUserOrToUser(from: String, to: String)
    fun findAllByFromUserAndToUser(from: String, to: String): List<Message>
    fun findAllByFromUserOrToUser(from: String, to: String): List<Message>
}