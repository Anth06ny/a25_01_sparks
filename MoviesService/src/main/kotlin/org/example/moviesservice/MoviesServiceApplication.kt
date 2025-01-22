package org.example.moviesservice

import org.example.moviesservice.model.Movie
import org.example.moviesservice.model.MovieRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MoviesServiceApplication(val movieRepository: MovieRepository) : CommandLineRunner {


    override fun run(vararg args: String?) {

        if(movieRepository.count() == 0L){
            movieRepository.saveAll(
                arrayListOf(
                    Movie(title = "Star Wars", length = 120),
                    Movie(title = "Mad max", length = 130),
                    Movie(title = "Memento", length = 150),
                    Movie(title = "Inception", length = 220)
                )
            )
        }

    }

}

fun main(args: Array<String>) {
    runApplication<MoviesServiceApplication>(*args)
}
