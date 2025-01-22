package org.example.a25_01_sparks.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.stubbing.Answer
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.util.AssertionErrors.assertTrue
import java.util.*
import kotlin.collections.ArrayList


@ExtendWith(MockitoExtension::class)
class MessageServiceTest() {


    @Mock
    lateinit var messageRepository: MessageRepository // = Mockito.mock(MessageRepository::class.java)

    //@Autowired
    @InjectMocks
    lateinit var messageService: MessageService


    @Test
    fun addMessage() {

        Mockito.`when`(messageRepository.save(Mockito.any(MessageBean::class.java))).thenAnswer{ invocation: InvocationOnMock ->
                val message = invocation.getArgument<MessageBean>(0)
                // Simuler l'attribution d'un ID
                message.id = 1L
                message
            }


        val messageBean = MessageBean(pseudo = "Alice", message = "Hello World")
        messageService.addMessage(messageBean)


        Mockito.verify(messageRepository, times(1)).save(messageBean)


        Assertions.assertNotNull(messageBean.id, "Id non ajouté");
        assertTrue("L'id n'a pas été modifié", messageBean.id == 1L )

        // Vérification que messageRepository.save() a été appelé avec le bon argument
        Mockito.`when`(messageRepository.findById(1L)).thenReturn(Optional.of(messageBean.copy()))

        val inDatabase = messageService.findById(messageBean.id!!)
        assertEquals(messageBean, inDatabase, "Les attributs sont différents")
        assertNotSame(messageBean, inDatabase, "C'est la même instance de message")

    }

    @Test
    fun get10Last() {

        val listMessages = ArrayList<MessageBean>()

        repeat(15) {
            val message = MessageBean(pseudo = "User$it", message = "Message $it")
            messageService.addMessage(message)
            listMessages += message
        }

        Mockito.`when`(messageRepository.findFirst10ByOrderByIdDesc())
            .thenReturn(listMessages.takeLast(10).reversed())

        var messages = messageService.get10Last()

        // Vérifications
        Assertions.assertEquals(10, messages.size)
        Assertions.assertEquals("Message 14", messages[0].message)
    }
}