package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class DemoApplication {

    @Bean
    fun route(handler: RatingHandler) = router {
        (GET("/ratings") and accept(TEXT_EVENT_STREAM)).invoke(handler::fetchRatings)
    }
}

fun main(args: Array<String>) {
    run(DemoApplication::class, *args)
}
