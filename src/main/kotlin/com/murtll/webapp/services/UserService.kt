package com.murtll.webapp.services

import com.murtll.webapp.entities.User
import com.murtll.webapp.repos.UsersRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService {
    private lateinit var usersRepo: UsersRepo
    @Autowired
    private lateinit var friendService: FriendService
    @Autowired
    private lateinit var messageService: MessageService

    @Autowired
    fun setFriendRepo(_userRepo: UsersRepo) {
        usersRepo = _userRepo
    }

    fun getAllUsers(): List<User> {
        return usersRepo.findAll()
    }

    fun getUserByUsername(username: String): User {
        return usersRepo.findOneByUsername(username)
    }

    fun unableUserByUsername(username: String) {
//        usersRepo.deleteByUsername(username)
        val updatedUser = usersRepo.findOneByUsername(username)
        updatedUser.enabled = false
        usersRepo.save(updatedUser)
    }

    fun addUser (username: String, password: String) {
        val newUser = User(username, "{noop}$password", true)
        usersRepo.save(newUser)
    }

    fun existUser(username: String): Boolean {
        usersRepo.findAll().forEach {
            if (it.username == username) return true
        }
        return false
    }

    fun reviveUserByUsername(username: String) {
        val updatedUser = usersRepo.findOneByUsername(username)
        updatedUser.enabled = true
        usersRepo.save(updatedUser)
    }

    fun deleteUserByUsername(username: String) {
        friendService.deleteAllFriendsOfUser(username)
        messageService.deleteAllUserMessages(username)
        usersRepo.deleteByUsername(username)

    }
}