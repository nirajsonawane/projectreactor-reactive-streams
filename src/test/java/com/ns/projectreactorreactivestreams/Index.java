package com.ns.projectreactorreactivestreams;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.List;

public class Index {

    @Test
    public  void maintainIndex(){

        Flux<Tuple2<Long, String>> index = Flux
                .just("First", "Second", "Third")
                .index();
    StepVerifier.create(index)
                .expectNext(Tuples.of(0L,"First"))
                .expectNext(Tuples.of(1L,"Second"))
                .expectNext(Tuples.of(2L,"Third"))
                .verifyComplete();

    }


}
