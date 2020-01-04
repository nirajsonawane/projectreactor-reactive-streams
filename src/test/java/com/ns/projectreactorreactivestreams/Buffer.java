package com.ns.projectreactorreactivestreams;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class Buffer {


    @Test
    public void bufferTest() {
        Flux<List<Integer>> buffer = Flux
                .just(1, 2, 3, 4, 5, 6, 7)
                .buffer(2);

        StepVerifier
                .create(buffer)
                .expectNext(Arrays.asList(1, 2))
                .expectNext(Arrays.asList(3, 4))
                .expectNext(Arrays.asList(5, 6))
                .expectNext(Arrays.asList(7))
                .verifyComplete();

    }

    @Test
    public void bufferAll() {
        Flux<List<Integer>> buffer = Flux
                .just(1, 2, 3, 4, 5, 6, 7)
                .buffer();

        StepVerifier
                .create(buffer)
                .expectNext(Arrays.asList(1, 2,3,4,5,6,7))
                .verifyComplete();

    }
}
