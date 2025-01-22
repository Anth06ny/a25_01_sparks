package org.example.a25_01_sparks.controller

import jakarta.servlet.http.HttpSession
import org.example.a25_01_sparks.config.CHANNEL_NAME
import org.example.a25_01_sparks.config.WebSocketConfig
import org.example.a25_01_sparks.model.MessageBean
import org.example.a25_01_sparks.model.MessageService
import org.example.a25_01_sparks.model.StudentBean
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/ws")
class MyController(private val messagingTemplate: SimpMessagingTemplate, val messageService: MessageService) {

    //http://localhost:8080/hello
    @GetMapping("/hello")
    fun testHello(model: Model, httpSession: HttpSession): String {

        val liststudent = ArrayList<StudentBean>()
        liststudent.add(StudentBean("Toto", 12))
        liststudent.add(StudentBean("Tata", 14))



        model.addAttribute("texte", "Bonjour ${httpSession.id}" )
        model.addAttribute("studentBean", StudentBean("Toto", 12))
        model.addAttribute("studentList", liststudent)

        return "welcome"
    }

    @MessageMapping("/chat")
    fun receiveMessage(message: MessageBean) {
        println("/ws/chat $message")

        messageService.addMessage(message)

        // Envoyer la liste des messages sur le channel
        //Si la variable est dans le même package il faut enlever WebSocketConfig.
        messagingTemplate.convertAndSend(CHANNEL_NAME, messageService.allMessage())
    }

}