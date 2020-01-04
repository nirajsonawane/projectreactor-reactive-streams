package com.ns.projectreactorreactivestreams;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class MapOperationTest {

    @Test
    public void testFilteringFlux() {

        Flux<String> fluxFromJust = Flux.just("RandomString", "SecondString","XCDFRG").log();
        Flux<Integer> filter = fluxFromJust.map(i-> i.length());
        StepVerifier
                .create(filter)
                .expectNext(12,12,6)
                .verifyComplete();
    }
}
