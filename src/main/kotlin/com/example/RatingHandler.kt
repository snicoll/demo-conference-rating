package com.example

import org.springframework.http.MediaType.*
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.body
import java.time.Duration.*

@Component
class RatingHandler(val generator: RatingGenerator) {

    fun fetchRatings(request: ServerRequest) = ok()
            .contentType(TEXT_EVENT_STREAM)
            .body(generator.fetchRatingStream(ofMillis(200)))

}
