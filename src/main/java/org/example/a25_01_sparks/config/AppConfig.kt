package org.example.a25_01_sparks.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient


@Configuration
open class AppConfig {

    //Pour des sites externes
    @Bean
    @Qualifier("moviesAPIClient")
    open fun moviesAPIClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://localhost:8081/movies/")
            .build()
    }

    //Pour Utiliser avec le LoadBalancing et Eureka
    @Bean
    @Qualifier("moviesAPIClientWithLoadBalancing")
    @LoadBalanced
    open fun moviesAPIClientWithLoadBalancing(lbFunction: ReactorLoadBalancerExchangeFilterFunction?): WebClient {
        return WebClient.builder()
            .baseUrl("http://MoviesService/movies/")
            .filter(lbFunction!!)
            .build()
    }


}