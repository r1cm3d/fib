package com.github.r1cm3d.domain;

import io.smallrye.mutiny.Uni;

import java.math.BigInteger;
import java.time.Duration;

import static io.smallrye.mutiny.infrastructure.Infrastructure.getDefaultExecutor;
import static java.time.Duration.ofMillis;

public final class AsyncFibonacci {

    private static final Duration DEFAULT_TIMEOUT = ofMillis(800);
    private static final int LARGE_NUMBER_THRESHOLD = 4000000;

    private AsyncFibonacci() {}

    public static Uni<String> calc(int n) {
        return (n >= LARGE_NUMBER_THRESHOLD) ? blockingCalc(n) : nonBlockingCalc(n);
    }

    static Uni<String> blockingCalc(int n) {
        return Uni.createFrom()
                .item(n)
                .map(Fibonacci::calc)
                .runSubscriptionOn(getDefaultExecutor())
                .map(BigInteger::toString);
    }

    static Uni<String> nonBlockingCalc(int n) {
        return Uni.createFrom()
                .item(n)
                .map(Fibonacci::calc)
                .ifNoItem().after(DEFAULT_TIMEOUT).fail()
                .map(BigInteger::toString);
    }
}
