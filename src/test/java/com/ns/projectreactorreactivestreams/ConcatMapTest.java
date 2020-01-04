package com.ns.projectreactorreactivestreams;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class ConcatMapTest {

    @Test
    public void testFlatMapFlux() {

        Flux<Integer> fluxFromJust = Flux.just(1,2,3).log();
        Integer integer = fluxFromJust
                .flatMap(i -> getSomeFlux(i))
                .blockLast();
        System.out.println(integer);

        //getSomeFlux returns flux of range ,
// then we do flatMap on all Flux to convert them in to single Flux
        //integerFlux.subscribe(System.out::println,e->e.printStackTrace(),()-> System.out.println("Done"));


        /*StepVerifier
                .create(integerFlux)
                .expectNextCount(30)
                .verifyComplete();*/
    }

    private Flux<Integer> getSomeFlux(Integer i) {
        return Flux.range(i*10,10).delayElements(Duration.ofSeconds(1));
    }
}
