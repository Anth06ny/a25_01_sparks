package org.example.a25_01_sparks.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service


@Service
class MessageService(var messageRepository: MessageRepository) {


    fun addMessage(messageBean: MessageBean) {
        messageRepository.save(messageBean)
    }

    fun allMessage() = messageRepository.findAll()

    fun get10Last() = messageRepository.findFirst10ByOrderByIdDesc()

    fun findById(id:Long) = messageRepository.findByIdOrNull(id)

}


//Classe d'accès à la table message
@Repository                       //<Bean, Typage Id>
interface MessageRepository : JpaRepository<MessageBean, Long> {
    fun findFirst10ByOrderByIdDesc() : List<MessageBean>
}

@Entity
@Table(name = "message")
data class MessageBean(

    @Id ///ID auto incrémenté
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @field:NotBlank(message = "Le pseudo ne doit pas être vide")
    var pseudo: String = "",
    var message: String = ""
)