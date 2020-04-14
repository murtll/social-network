package com.murtll.webapp.services

import com.murtll.webapp.controllers.MainController
import com.murtll.webapp.entities.FriendItem
import com.murtll.webapp.entities.User
import com.murtll.webapp.repos.FriendsRepo
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
class FriendService {
    var friendList = mutableSetOf<FriendItem>()
    private lateinit var usersRepo: UsersRepo
    private lateinit var friendsRepo: FriendsRepo

    @Autowired
    fun setUsersRepo(_usersRepo: UsersRepo) {
        usersRepo = _usersRepo
    }

    @Autowired
    fun setFriendsRepo(_friendsRepo: FriendsRepo) {
        friendsRepo = _friendsRepo
    }

    fun addByUsername(username: String, principal: Principal) {
        val newFriend = usersRepo.findOneByUsername(username)
        val currentUser = usersRepo.findOneByUsername(principal.name)
        val newFriendItem = FriendItem(null, currentUser, newFriend)
        friendList.add(newFriendItem)
        friendsRepo.save(newFriendItem)
    }

    fun getFriendsFromDB(principal: Principal) {
        val currentUser = usersRepo.findOneByUsername(principal.name)
        friendList = friendsRepo.findAllByMainUser(currentUser).toMutableSet()
    }

    fun deleteByUsername(username: String, principal: Principal) {
        friendList.removeIf { it.friendUser?.username == username }
        val friend = usersRepo.findOneByUsername(username)
        val currentUser = usersRepo.findOneByUsername(principal.name)
        friendsRepo.deleteByMainUserAndFriendUser(currentUser, friend)
    }

    fun deleteAllFriendsOfUser(username: String) {
        val user = usersRepo.findOneByUsername(username)
        friendsRepo.deleteAllByFriendUserOrMainUser(user, user)
    }

    fun isFriend(user: User): Boolean {
        friendList.forEach {
            if (it.friendUser?.username == user.username) return true
        }
        return false
    }
}