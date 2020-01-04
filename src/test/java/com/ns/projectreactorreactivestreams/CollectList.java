package com.ns.projectreactorreactivestreams;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class CollectList {

    @Test
    public void  CollectList(){
        Mono<List<Integer>> listMono = Flux
                .just(1, 2, 3)
                .collectList();
        StepVerifier.create(listMono)
                .expectNext(Arrays.asList(1,2,3))
                .verifyComplete();
    }

    @Test
    public void  CollectSortedListList(){
        Mono<List<Integer>> listMono = Flux
                .just(1, 2, 3,9,8)
                .collectSortedList();
        StepVerifier.create(listMono)
                .expectNext(Arrays.asList(1,2,3,8,9))
                .verifyComplete();
    }
}
