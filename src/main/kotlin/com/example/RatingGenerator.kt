package com.example

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.*

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
