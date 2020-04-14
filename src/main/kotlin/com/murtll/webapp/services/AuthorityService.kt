package com.murtll.webapp.services

import com.murtll.webapp.controllers.MainController
import com.murtll.webapp.entities.Authority
import com.murtll.webapp.entities.FriendItem
import com.murtll.webapp.entities.User
import com.murtll.webapp.repos.AuthoritiesRepo
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
class AuthorityService {
    private lateinit var authoritiesRepo: AuthoritiesRepo

    @Autowired
    fun setAuthoritiesRepo(_authoritiesRepo: AuthoritiesRepo) {
        authoritiesRepo = _authoritiesRepo
    }

    fun addRole(username: String, role: String) {
        val newAuthority = Authority(username, role)
        authoritiesRepo.save(newAuthority)
    }

    fun unableByUsername(username: String) {
        authoritiesRepo.findById(username).get().authority = "ROLE_NONE"
    }

    fun reviveByUsername(username: String) {
        authoritiesRepo.findById(username).get().authority = "ROLE_USER"

    }

    fun deleteByUsername(username: String) {
        authoritiesRepo.deleteByUserName(username)
    }
}