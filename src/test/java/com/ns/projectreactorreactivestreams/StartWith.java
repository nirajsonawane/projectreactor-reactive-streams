package com.ns.projectreactorreactivestreams;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class StartWith {

    @Test
    public void  startWith(){
        Flux<Integer> just = Flux.just(1, 2, 3);
        Flux<Integer> integerFlux = just.startWith(0);
        StepVerifier.create(integerFlux)
                .expectNext(0,1,2,3)
                .verifyComplete();
    }
}
