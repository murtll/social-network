package com.murtll.webapp.controllers

import com.murtll.webapp.entities.FriendItem
import com.murtll.webapp.services.AuthorityService
import com.murtll.webapp.services.FriendService
import com.murtll.webapp.services.MessageService
import com.murtll.webapp.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.security.Principal

@Controller
object MainController {

    private lateinit var userService: UserService

    private lateinit var friendService: FriendService

    private lateinit var authorityService: AuthorityService

    private lateinit var messageService: MessageService

    @Autowired
    fun setMessageService(_messageService: MessageService) {
        messageService = _messageService
    }

    @Autowired
    fun setAuthoritiesRepo(_authorityService: AuthorityService) {
        authorityService = _authorityService
    }

    @Autowired
    fun setFriends(_friendService: FriendService) {
        friendService = _friendService
    }

    @Autowired
    fun setUserService(_userService: UserService) {
        userService = _userService
    }

    @GetMapping("/index")
    fun homePage() = "index"

    @GetMapping("/error")
    fun showError() = "error"

    @GetMapping("/users")
    fun friendsPage(model: Model): String {
        model.addAttribute("users", userService.getAllUsers())
        return "users"
    }

    @GetMapping("/page/{name}")
    fun userPage(model: Model, @PathVariable("name") name: String, principal: Principal): String {
            val userItem = FriendItem(null, userService.getUserByUsername(principal.name), userService.getUserByUsername(name))
            model.addAttribute("user", userItem)
            val isItMe = name == principal.name
            val isItFriendOfCurrent = friendService.isFriend(userService.getUserByUsername(name))
            model.addAttribute("is_friend", isItFriendOfCurrent)
            model.addAttribute("is_me", isItMe)
            return "user-page"
    }

    @GetMapping("/mypage")
    fun myPage(model: Model, principal: Principal): String {

        val userItem = FriendItem(null, userService.getUserByUsername(principal.name), userService.getUserByUsername(principal.name))

        val isItMe = true
        val isItFriendOfCurrent = false
        model.addAttribute("is_friend", isItFriendOfCurrent)
        model.addAttribute("user", userItem)
        model.addAttribute("is_me", isItMe)
        return "user-page"
    }

    @GetMapping("/login")
    fun loginPage(): String {
        return "login"
    }

    @GetMapping("/users/unable/{name}")
    fun unableUser(model: Model, @PathVariable("name") name: String): String {
        authorityService.unableByUsername(name)
        userService.unableUserByUsername(name)
        model.addAttribute("users", userService.getAllUsers())
        return "redirect:/page/$name"
    }

    @GetMapping("/users/revive/{name}")
    fun reviveUser(model: Model, @PathVariable("name") name: String): String {
        authorityService.reviveByUsername(name)
        userService.reviveUserByUsername(name)
        return "redirect:/page/$name"
    }

    @GetMapping("/users/delete/{name}")
    fun deleteUser(model: Model, @PathVariable("name") name: String): String {
        authorityService.deleteByUsername(name)
        userService.deleteUserByUsername(name)
//        model.addAttribute("users", userService.getAllUsers())
        return "redirect:/users"
    }

    @GetMapping("/all_messages")
    fun messagesPage(model: Model, principal: Principal): String {
        val toUsers = messageService.getMessagesByFromOrTo(principal.name).map { it.toUser }
        val fromUsers = messageService.getMessagesByFromOrTo(principal.name).map { it.fromUser }
        val messagedUsernames = userService.getAllUsers().map { it.username }.filter { toUsers.contains(it) || fromUsers.contains(it)}
        val messagedUsers = userService.getAllUsers().filter { messagedUsernames.contains(it.username) }
        model.addAttribute("users", messagedUsers)
        return "all_messages"
    }
}