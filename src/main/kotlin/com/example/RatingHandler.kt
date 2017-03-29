package com.example

import java.time.Duration

import reactor.core.publisher.Mono

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux

@Component
class RatingHandler(val generator: RatingGenerator) {

    fun fetchRatings(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body<Rating, Flux<Rating>>(this.generator.fetchRatingStream(Duration.ofMillis(200)), Rating::class.java)
    }

}
