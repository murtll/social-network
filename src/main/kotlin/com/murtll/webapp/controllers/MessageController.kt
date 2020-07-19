package com.murtll.webapp.controllers

import com.murtll.webapp.entities.Message
import com.murtll.webapp.services.MessageService
import com.murtll.webapp.utils.Register
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.time.LocalDate
import java.time.LocalTime

@Controller
@RequestMapping("/messages")
object MessageController {
    private lateinit var messageService: MessageService

    @Autowired
    fun setMessageService(_messageService: MessageService) {
        messageService = _messageService
    }

    @RequestMapping("/{name}", method = [RequestMethod.GET])
    fun showMessages(model: Model, @PathVariable("name") name: String, principal: Principal): String {
        val messages = messageService.getMessagesByFromAndTo(name, principal.name).toMutableList()
        messages.addAll(messageService.getMessagesByFromAndTo(principal.name, name))
        messages.sortBy { it.id }
        model.addAttribute("all_messages", messages)
        model.addAttribute("message", Message())
        model.addAttribute("username", name)
        return "messages"
    }

    @RequestMapping("/{name}/send", method = [RequestMethod.POST])
    fun sendMessage(@ModelAttribute message: Message, model: Model, principal: Principal, @PathVariable("name") name: String): String {
        message.run {
            fromUser = principal.name
            toUser = name
            time = LocalTime.now().hour * 60 + LocalTime.now().minute
            date = LocalDate.now().year * 365 + LocalDate.now().month.value * 30 + LocalDate.now().dayOfMonth
        }
        messageService.writeMessageToDB(message)
        return "redirect:/messages/$name"
    }

}