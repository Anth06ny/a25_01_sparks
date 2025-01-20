package org.example.a25_01_sparks.restcontroller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.a25_01_sparks.model.MessageBean
import org.springframework.web.bind.annotation.*

//@Tag, @Operation,  @ApiResponses :  pour la documentation avec Swagger

//@Tag, @Operation,  @ApiResponses :  pour la documentation avec Swagger

@RestController
@RequestMapping("/tchat")
@Tag(name = "Tchat", description = "API pour gérer les messages d'un tchat")
class TchatRestController {

    private val list: MutableList<MessageBean> = ArrayList()

    //Jeu de données
    //init {
    //repeat(5) {
    //    list.add(MessageBean("Toto", "Coucou"))
    //    list.add(MessageBean("Tata", "hello"))
    //    list.add(MessageBean("Toto", "hello"))
    //    list.add(MessageBean("Tata", "Coucou"))
    //}
    //}

    @Operation(
        summary = "Enregistrer un message",
        description = "Permet d'enregistrer un nouveau message envoyé par un utilisateur"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Message enregistré avec succès"
            )
        ]
    )
    @PostMapping("/saveMessage")
    fun saveMessage(@RequestBody message: MessageBean) {
        println("/saveMessage : ${message.message} : ${message.pseudo}")
        list.add(message)
    }

    @Operation(
        summary = "Obtenir les derniers messages",
        description = "Récupère les 10 derniers messages enregistrés dans le tchat"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Liste des messages retournée avec succès",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = MessageBean::class)
                )]
            )
        ]
    )
    @GetMapping("/allMessages")
    fun allMessages(): List<MessageBean> {
        println("/allMessages")

        // Pour ne retourner que les 10 derniers
        return list.takeLast(10)
    }

    // http://localhost:8080/tchat/filter?filter=coucou&pseudo=toto
    @GetMapping("/filter")
    fun filter(filter: String? = null, pseudo: String? = null): List<MessageBean> {
        println("/filter filter=$filter pseudo=$pseudo")

        return list.filter {
            //Soit on n'a pas de filtre soit on garde ceux qui correspondent aux filtres
            (filter == null || it.message.contains(filter, true))
                    &&
                    (pseudo == null || it.pseudo.equals(pseudo, true))
        }
    }
}