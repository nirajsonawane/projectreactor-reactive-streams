package com.ns.projectreactorreactivestreams;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;
import reactor.test.StepVerifier;

import java.sql.Time;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
@Slf4j
public class ErrorHandling {

    @Test
    public void testErrorFlowFlux() {

        Flux<Integer> fluxFromJust = Flux.just(1, 2,3,4,5)
                                         .concatWith(Flux.error(new RuntimeException("Test")))
                                         .concatWith(Flux.just(6));

        fluxFromJust.subscribe(
                (it)-> System.out.println("Number is " + it),  // OnNext
                (e) -> e.printStackTrace(),                    //OnError
                () -> System.out.println("subscriber Complited") //onComplete
                );

        StepVerifier
                .create(fluxFromJust)
                .expectNext(1, 2,3,4,5)
                .expectError(RuntimeException.class);

    }
    @Test
    public void testOnErrorResume() throws InterruptedException {

        Flux<Integer> fluxFromJust = Flux.just(1, 2,3,4,5)
                .concatWith(Flux.error(new RuntimeException("Test")))
                .concatWith(Flux.just(6))
                .onErrorResume(e->{
                    log.info("**************");
                    System.out.println("Exception occured " + e.getMessage());
                    //Return Some Fallback Values
                    return Flux.just(7,8);
                });
        StepVerifier
                .create(fluxFromJust)
                .expectNext(1, 2,3,4,5)
                .expectNext(7,8)
               .verifyComplete();

    }
    @Test
    public void testOnErrorReturn() throws InterruptedException {

        Flux<Integer> fluxFromJust = Flux.just(1, 2,3,4,5)
                .concatWith(Flux.error(new RuntimeException("Test")))
                .concatWith(Flux.just(6))
                .onErrorReturn(99)
               ;
        StepVerifier
                .create(fluxFromJust)
                .expectNext(1, 2,3,4,5)
                .expectNext(99)
                .verifyComplete();

    }
    @Test
    public void testOnErrorContinue() throws InterruptedException {

        Flux<Integer> fluxFromJust = Flux.just(1, 2,3,4,5)
                .concatWith(Flux.error(new RuntimeException("Test")))
                .concatWith(Flux.just(6))
                .map(i->mapSomeValue(i))
                .onErrorContinue((e,i)->{
                    System.out.println("Error For Item +" + i );
                })
                ;
        StepVerifier
                .create(fluxFromJust)
                .expectNext(1, 2,4,5)
                .expectError();

    }

    @Test
    public void testDoOnError() throws InterruptedException {

        Flux<Integer> fluxFromJust = Flux.just(1, 2,3,4,5)
                .concatWith(Flux.error(new RuntimeException("Test")))
                .doOnError(e -> System.out.println("Rum some Side effect!!"));

        StepVerifier
                .create(fluxFromJust)
                .expectNext(1, 2,3,4,5)
                .expectError()
                .verify();
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void testDoFinally() throws InterruptedException {
        Flux<Integer> fluxFromJust = Flux.just(1, 2,3,4,5)
                .concatWith(Flux.error(new RuntimeException("Test")))
                .doFinally( i->{
                    if (SignalType.ON_ERROR.equals(i)) {
                        System.out.println("Completed with Error ");
                    }
                    if (SignalType.ON_COMPLETE.equals(i)) {
                        System.out.println("Completed without Error ");
                    }
                });

        StepVerifier
                .create(fluxFromJust)
                .expectNext(1, 2,3,4,5)
                .expectError()
                .verify();
        TimeUnit.SECONDS.sleep(2);
    }

    private int mapSomeValue(Integer i) {

        if( i==3)
            throw new RuntimeException("Exception From Map");
        return i;
    }

    @Test
    public void testOnErrorMap() throws InterruptedException {

        Flux<Integer> fluxFromJust = Flux.just(1, 2,3,4,5)
                .concatWith(Flux.error(new RuntimeException("Test")))
                .concatWith(Flux.just(6))
                .map(i->i*2)
                .onErrorMap(e -> new CustomeException(e) )
                ;
        StepVerifier
                .create(fluxFromJust)
                .expectNext(2, 4,6,8,10)
                .expectError(CustomeException.class);
    }
    @Test
    public void testOnRetry() throws InterruptedException {

        Flux<Integer> fluxFromJust = Flux.just(1, 2,3)
                .concatWith(Flux.error(new RuntimeException("Test")))
                .concatWith(Flux.just(6))
                .map(i->doubleValue(i))
                .onErrorMap(e -> new CustomeException(e) )
                .retry(2)

                ;
        StepVerifier
                .create(fluxFromJust)
                .expectNext(2, 4,6)
                .expectNext(2, 4,6)
                .expectNext(2, 4,6)
                .expectError(CustomeException.class)
                .verify();
    }
    @Test
    public void testRetryBackoff() throws InterruptedException {

        Flux<Integer> fluxFromJust = Flux.just(1, 2,3)
                .concatWith(Flux.error(new RuntimeException("Test")))
                .concatWith(Flux.just(6))
                .map(i->doubleValue(i))
                .onErrorMap(e -> new CustomeException(e) )
                .retryBackoff(2, Duration.ofSeconds(1))

                ;
        StepVerifier
                .create(fluxFromJust)
                .expectNext(2, 4,6)
                .expectNext(2, 4,6)
                .expectNext(2, 4,6)
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    public void testOnErrorStop() throws InterruptedException {

        Flux<Integer> fluxFromJust = Flux.just(1, 2,3)
                .concatWith(Flux.error(new RuntimeException("Test")))
                .concatWith(Flux.just(6))
                .map(i->doubleValue(i))
                .onErrorStop()

                ;
        StepVerifier
                .create(fluxFromJust)
                .expectNext(2, 4,6)
                .verifyError();
    }

    private Integer doubleValue(Integer i) {
        System.out.println("Doing Multiple");
        return i*2;
    }
}
