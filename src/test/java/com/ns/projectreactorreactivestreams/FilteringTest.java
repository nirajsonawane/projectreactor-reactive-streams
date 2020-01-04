package com.ns.projectreactorreactivestreams;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FilteringTest {

    @Test
    public void testFilteringFlux() {

        Flux<Integer> fluxFromJust = Flux.just(1, 2,3,4,5,6,7,8,9,10).log();
        Flux<Integer> filter = fluxFromJust.filter(i -> i % 2 == 0);
        StepVerifier
                .create(filter)
                .expectNext(2,4,6,8,10)
                .verifyComplete();
    }
    @Test
    public void distinct() {

        Flux<Integer> fluxFromJust = Flux.just(1, 2,3,4,5,1,2,3,4,5).log();
        Flux<Integer> distinct = fluxFromJust.distinct();
        StepVerifier
                .create(distinct)
                .expectNext(1, 2,3,4,5)
                .verifyComplete();
    }

    @Test
    public void takeWhile() {

        Flux<Integer> fluxFromJust = Flux.just(1, 2,3,4,5,6,7,8,9,10).log();
        Flux<Integer> takeWhile = fluxFromJust.takeWhile(i -> i <=5);
        StepVerifier
                .create(takeWhile)
                .expectNext(1, 2,3,4,5)
                .verifyComplete();
    }
    @Test
    public void skipWhile() {

        Flux<Integer> fluxFromJust = Flux.just(1, 2,3,4,5,6,7,8,9,10).log();
        Flux<Integer> takeWhile = fluxFromJust.skipWhile(i -> i <=5);
        StepVerifier
                .create(takeWhile)
                .expectNext(6,7,8,9,10)
                .verifyComplete();
    }
}
