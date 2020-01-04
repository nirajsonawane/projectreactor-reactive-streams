package com.ns.projectreactorreactivestreams;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class CreatingFluxAndMono {

    @Test
    public void testCreateFluxAndSubscribe() {

        Flux<Integer> fluxFromJust = Flux.just(1, 2, 3).log();
        StepVerifier
                .create(fluxFromJust)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .verifyComplete();
    }
    @Test
    public void testCreateMonoAndSubscribe() {

        Mono<Integer> log = Mono
                .just(1)
                .log();
        StepVerifier
                .create(log)
                .expectNext(1)
                .verifyComplete();
    }
    @Test
    public void testCreateFluxAndSubscribeVerifyCount() {

        Flux<Integer> fluxFromJust = Flux.just(1, 2, 3).log();
        StepVerifier
                .create(fluxFromJust)
                .expectNextCount(4)
                .verifyComplete();
    }
    @Test
    public void testCreateFluxAndSubscribeVerifyError() {

        Flux<Integer> fluxFromJust = Flux.just(1).concatWith(Flux.error(new RuntimeException("Test"))).log();
        StepVerifier
                .create(fluxFromJust)
                .expectNextCount(1)
                .verifyError(RuntimeException.class)
                ;
    }
}
