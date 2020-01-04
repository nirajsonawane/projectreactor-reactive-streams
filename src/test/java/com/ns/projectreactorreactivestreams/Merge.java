package com.ns.projectreactorreactivestreams;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Merge {

    @Test
    public void zip() throws InterruptedException {
        Flux<Integer> firsFlux = Flux.just(1, 2, 3,4,5).delayElements(Duration.ofSeconds(1));
        Flux<Integer> secondFlux = Flux.just(10, 20, 30, 40).delayElements(Duration.ofSeconds(1));
        firsFlux.mergeWith(secondFlux).subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(11);
     }
}
