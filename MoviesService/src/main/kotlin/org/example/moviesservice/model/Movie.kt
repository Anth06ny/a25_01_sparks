package org.example.moviesservice.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

//Classe d'accès à la table message
@Repository                       //<Bean, Typage Id>
interface MovieRepository : JpaRepository<Movie, Long> {
}

@Entity
class Movie (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String? = null,
    var length: Int? = null
)