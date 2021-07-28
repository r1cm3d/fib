package com.github.r1cm3d.domain;

import io.smallrye.mutiny.TimeoutException;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.Test;

class AsyncFibonacciServiceTest {

    @Test
    void shouldHandleLargeIntNumber() {
        final int MAX_TESTED_ON_MY_MACHINE = 4000000;
        Uni<String> uni = AsyncFibonacciService.blockingCalc(MAX_TESTED_ON_MY_MACHINE);

        UniAssertSubscriber<String> subscriber = uni.subscribe().withSubscriber(UniAssertSubscriber.create());

        subscriber.assertNotTerminated();
    }

    @Test
    void shouldFailedWithTimeoutAfterOneSecond() {
        final int LARGE_NUMBER_WHICH_FAILS_LOCALLY = 10520000;
        Uni<String> uni = AsyncFibonacciService.nonBlockingCalc(LARGE_NUMBER_WHICH_FAILS_LOCALLY);

        UniAssertSubscriber<String> subscriber = uni.subscribe().withSubscriber(UniAssertSubscriber.create());

        subscriber.assertFailedWith(TimeoutException.class);
    }
}
