package com.murtll.webapp.services

import com.murtll.webapp.controllers.MainController
import com.murtll.webapp.entities.FriendItem
import com.murtll.webapp.entities.Message
import com.murtll.webapp.entities.User
import com.murtll.webapp.repos.FriendsRepo
import com.murtll.webapp.repos.MessagesRepo
import com.murtll.webapp.repos.UsersRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Service
import org.springframework.web.context.WebApplicationContext
import java.security.Principal
import javax.annotation.PostConstruct

@Service
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
class MessageService {
    private lateinit var messagesRepo: MessagesRepo
    private lateinit var friendsRepo: FriendsRepo
    private val messageList = mutableListOf<Message>()

    @Autowired
    fun setMessagesRepo(_messagesRepo: MessagesRepo) {
        messagesRepo = _messagesRepo
    }

    @Autowired
    fun setFriendsRepo(_friendsRepo: FriendsRepo) {
        friendsRepo = _friendsRepo
    }

    fun getMessagesByFromAndTo(from: String, to: String): List<Message> {
        return messagesRepo.findAllByFromUserAndToUser(from, to).sortedBy { it.id }
    }

    fun writeMessageToDB(message: Message) {
        messagesRepo.save(message)
    }

    fun getMessagesByFromOrTo(username: String): List<Message> {
        return messagesRepo.findAllByFromUserOrToUser(username, username)
    }

    fun deleteAllUserMessages(username: String){
        val messages = messagesRepo.findAllByFromUserOrToUser(username, username)
        messages.forEach {
            messagesRepo.delete(it)
        }
    }
}