package com.murtll.webapp.controllers

import com.murtll.webapp.services.AuthorityService
import com.murtll.webapp.services.UserService
import com.murtll.webapp.utils.Register
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
object RegisterController {

    private lateinit var userService: UserService
    private lateinit var authorityService: AuthorityService

    @Autowired
    fun setUserService(_userService: UserService) {
        userService = _userService
    }

    @Autowired
    fun setAuthorityService(_authorityService: AuthorityService) {
        authorityService = _authorityService
    }


    @RequestMapping("/register", method = [RequestMethod.GET])
    fun registerForm(model: Model): String {
        model.addAttribute("register", Register())
        model.addAttribute("message", "Enter your name and password")
        return "register"
    }

    @RequestMapping("/register", method = [RequestMethod.POST])
    fun registerSubmit(@ModelAttribute register: Register, model: Model): String {
        if (!userService.existUser(register.userName.toString())){
            userService.addUser(register.userName.toString(), register.password.toString())
            authorityService.addRole(register.userName.toString(), "ROLE_USER")
            return "login"
        } else {
            model.addAttribute("message", "This username already exists, please choose another")
            return "register"
        }
    }
}