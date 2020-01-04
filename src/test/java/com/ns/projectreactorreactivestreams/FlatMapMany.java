package com.ns.projectreactorreactivestreams;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FlatMapMany {

    @Test
    public void flatMapManyTest() {
        Mono<List<Integer>> just = Mono.just(Arrays.asList(1, 2, 3));
        Flux<Integer> integerFlux = just.flatMapMany(it -> Flux.fromIterable(it));
        StepVerifier
                .create(integerFlux)
                .expectNext(1, 2, 3)
                .verifyComplete();
    }
}
