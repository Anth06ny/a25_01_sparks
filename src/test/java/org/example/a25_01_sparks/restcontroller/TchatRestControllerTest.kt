package org.example.a25_01_sparks.restcontroller

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.example.a25_01_sparks.model.MessageBean
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.util.AssertionErrors.assertTrue
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*
import kotlin.random.Random

@SpringBootTest
@AutoConfigureMockMvc
class TchatRestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val  authentification = "Basic " + Base64.getEncoder().encodeToString("aaa:bbb".toByteArray())

    @Test
    fun saveMessageEmptyPseudoTest() {

        //Test pseudo vide
        val requestJsonEmptyPseudo = """
            {
                "pseudo": "",
                "message": "coucou"
            }""".trimIndent()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/tchat/saveMessage")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJsonEmptyPseudo)
                .header("Authorization", authentification) // Ajout de Basic Auth

        )
            .andExpect(MockMvcResultMatchers.status().is4xxClientError()) //Code retour
    }

    @Test
    fun saveMessageTest() {
        val requestJson = """
            {
                "pseudo": "toto",
                "message": "coucou"
            }""".trimIndent()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/tchat/saveMessage")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header("Authorization", authentification) // Ajout de Basic Auth

        )
            .andExpect(MockMvcResultMatchers.status().isOk()) //Code retour

    }

    @Test
    fun allMessageTest() {

        val randomNumber = Random.nextInt()

        var pseudo = "toto$randomNumber"
        var message = "coucou$randomNumber"

        //On envoie un message
        val requestJson = """
            {
                "pseudo":"$pseudo",
                "message":"$message"
            }""".trimIndent()
        println(requestJson)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/tchat/saveMessage")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header("Authorization", authentification) // Ajout de Basic Auth
        )
            .andExpect(MockMvcResultMatchers.status().isOk()) //Code retour

        var result = mockMvc.perform(
            MockMvcRequestBuilders.get("/tchat/allMessages")
        )
            .andExpect(MockMvcResultMatchers.status().isOk()) //Code retour

        var dataResult = result.andReturn().response.contentAsString

        // Désérialiser la réponse JSON en une liste d'objets MessageBean
        var messages = ObjectMapper().readValue(dataResult, object : TypeReference<List<MessageBean>>() {})

        // Vérifier le dernier message
        assertTrue("Maximum 10 éléments", messages.size <= 10 )
        assertTrue("Minimum 1 element", messages.isNotEmpty() )
        assertEquals(pseudo, messages.first().pseudo,"pseudo attendu incorrect" )
        assertEquals(message , messages.first().message, "message attendu incorrect")
    }
}