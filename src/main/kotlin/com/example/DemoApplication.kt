package com.example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.*

@SpringBootApplication
class DemoApplication {

    @Bean
    fun route(handler: RatingHandler) = router {
        (GET("/ratings") and accept(TEXT_EVENT_STREAM)).invoke(handler::fetchRatings)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(DemoApplication::class.java, *args)
}

data class Rating(val conference: String, val rating: Int)

@Component
class RatingHandler(val generator: RatingGenerator) {

    fun fetchRatings(request: ServerRequest) = ServerResponse.ok()
            .contentType(TEXT_EVENT_STREAM)
            .body(generator.fetchRatingStream(Duration.ofMillis(200)))

}

@Component
class RatingGenerator {

    private val conferences = listOf(
            ConferenceDescriptor("Modern Component Design with Spring Framework 4.3", 5, 5),
            ConferenceDescriptor("From Zero to Hero with Spring Boot"),
            ConferenceDescriptor("Reactive Spring", 3, 5),
            ConferenceDescriptor("What's new in FooBar 1.0.4", 0, 1)
    )

    fun fetchRatingStream(period: Duration) =
            Flux.generate<Rating> { it.next(generateRandomRating(conferences[Random().nextInt(conferences.size)])) }
                    .zipWith(Flux.interval(period))
                    .map { it.t1 }
                    .share()
                    .log()


    private fun generateRandomRating(descriptor: ConferenceDescriptor) = Rating(
            descriptor.title,
            descriptor.minRating + (Math.random() * (descriptor.maxRating - descriptor.minRating + 1)).toInt()
    )


    private class ConferenceDescriptor(val title: String, val minRating: Int = 0, val maxRating: Int = 5)

}
