package com.ns.projectreactorreactivestreams;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FlatMap {

    @Test
    public void testFlatMapFlux() {

        Flux<Integer> fluxFromJust = Flux.just(1,2,3).log();
        Flux<Integer> integerFlux = fluxFromJust
                .flatMap(i -> getSomeFlux(i));//getSomeFlux returns flux of range ,
                                                // then we do flatMap on all Flux to convert them in to single Flux

        StepVerifier
                .create(integerFlux)
                .expectNextCount(30)
                .verifyComplete();
    }

    private Flux<Integer> getSomeFlux(Integer i) {
        return Flux.range(i,10);
    }
}
