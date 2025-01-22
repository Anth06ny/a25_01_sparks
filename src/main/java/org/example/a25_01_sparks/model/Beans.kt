package org.example.a25_01_sparks.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.Size

data class StudentBean(var name:String, var note:Int)



data class UserBean(var id: Long? = null, var login: String = "",
                    @field:Size(min = 3, message = "Il faut au moins 3 caractères") var password: String = "")

class MovieBean (
    var id: Long? = null,
    var title: String? = null,
    var length: Int? = null
)