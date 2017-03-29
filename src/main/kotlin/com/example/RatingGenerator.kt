package com.example

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.*

@Component
class RatingGenerator {

    private val conferences = Arrays.asList(
            ConferenceDescriptor("Modern Component Design with Spring Framework 4.3", 5, 5),
            ConferenceDescriptor("From Zero to Hero with Spring Boot"),
            ConferenceDescriptor("Reactive Spring", 3, 5),
            ConferenceDescriptor("What's new in FooBar 1.0.4", 0, 1)
    )

    fun fetchRatingStream(period: Duration): Flux<Rating> {
        return Flux.just(generateRandomRating(conferences.get(0)))
    }

    private fun generateRandomRating(descriptor: ConferenceDescriptor): Rating {
        val rating = descriptor.minRating + (Math.random() * (descriptor.maxRating - descriptor.minRating + 1)).toInt()
        return Rating(descriptor.title, rating)
    }

    private class ConferenceDescriptor(val title: String, val minRating: Int = 0, val maxRating: Int = 5)

}
