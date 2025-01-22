package org.example.a25_01_sparks.restcontroller

import org.example.a25_01_sparks.model.MovieBean
import org.example.a25_01_sparks.model.UserBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/mymovies")
class TestMoviesController {

    @Autowired //Laisse à Spring le remplissage
    //Si plusieurs objet de même type permet de les différencier
    @Qualifier("moviesAPIClient")
    private lateinit var clientWithoutLoadBalancing: WebClient

    @Autowired
    @Qualifier("moviesAPIClientWithLoadBalancing")
    private lateinit var clientWithLoadBalancing: WebClient


    //http://localhost:8080/mymovies/directAccess
    @GetMapping("/directAccess")
    fun directAccess(): MovieBean? {
        println("/directAccess");

        return clientWithoutLoadBalancing.get().uri("/{id}", 1).retrieve().bodyToMono(
            MovieBean::class.java
        ).block()

    }


    //http://localhost:8080/mymovies/directAccessReactive
    @GetMapping("/directAccessReactive")
    fun directAccessReactive(): Mono<MovieBean> {
        println("/directAccessReactive");

        return clientWithoutLoadBalancing.get().uri("/{id}", 1).retrieve().bodyToMono(
            MovieBean::class.java
        )
    }

    //http://localhost:8080/mymovies/eurekaAccess
    @GetMapping("/eurekaAccess")
    fun eurekaAccess() : MovieBean? {
        println("/eurekaAccess");

        return clientWithLoadBalancing.get().uri("/{id}", 1).retrieve().bodyToMono(
            MovieBean::class.java
        ).block()
    }

    //http://localhost:8080/mymovies/eurekaAccessReactive
    @GetMapping("/eurekaAccessReactive")
    fun eurekaAccessReactive() : Mono<MovieBean> {
        println("/eurekaAccessReactive");

        return clientWithLoadBalancing.get().uri("/{id}", 1).retrieve().bodyToMono(
            MovieBean::class.java
        )
    }
}