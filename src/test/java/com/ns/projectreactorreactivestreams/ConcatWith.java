package com.ns.projectreactorreactivestreams;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ConcatWith {

    @Test
    public void  concatWith(){
        Flux<Integer> just = Flux.just(1, 2, 3);
        Flux<Integer> integerFlux = just.concatWith(Flux.just(4));
        StepVerifier.create(integerFlux)
                .expectNext(1,2,3,4)
                .verifyComplete();
    }
}
