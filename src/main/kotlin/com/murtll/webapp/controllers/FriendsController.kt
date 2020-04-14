package com.murtll.webapp.controllers

import com.murtll.webapp.entities.Message
import com.murtll.webapp.services.FriendService
import com.murtll.webapp.services.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.security.Principal
import java.time.LocalDate
import java.time.LocalTime

@Controller
@RequestMapping("/friends")
object FriendsController {
    private lateinit var friendService: FriendService

    private lateinit var messageService: MessageService

    @Autowired
    fun setMessageService(_messageService: MessageService) {
        messageService = _messageService
    }

    @Autowired
    fun setFriends(_friendService: FriendService) {
        friendService = _friendService
    }

    @GetMapping("")
    fun friendsPage(model: Model, principal: Principal): String {
        friendService.getFriendsFromDB(principal)
        model.addAttribute("friends", friendService.friendList)
        return "friends"
    }

    @GetMapping("/add/{name}")
    fun addFriend(@PathVariable("name") name: String, principal: Principal): String {
        friendService.addByUsername(name, principal)
        val greetingMessage = Message(null,
                "added you to his friends", 
                principal.name,
                name,
                LocalTime.now().hour * 60 + LocalTime.now().minute,
                LocalDate.now().year * 365 + LocalDate.now().month.value * 30 + LocalDate.now().dayOfMonth)

        messageService.writeMessageToDB(greetingMessage)
        return "redirect:/page/$name"
    }

    @GetMapping("/delete/{name}")
    fun deleteFriend(@PathVariable("name") name: String, principal: Principal): String {
        friendService.deleteByUsername(name, principal)
        return "redirect:/page/$name"
    }

    @GetMapping("/users/delete/{name}")
    fun deleteUser(@PathVariable("name") name: String): String {
        friendService.deleteAllFriendsOfUser(name)
        return "redirect:/users/delete/$name"
    }

}