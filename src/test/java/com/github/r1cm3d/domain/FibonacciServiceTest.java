package com.github.r1cm3d.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.stream.Stream;

import static java.math.BigInteger.*;
import static java.math.BigInteger.ZERO;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.hamcrest.MatcherAssert.assertThat;

class FibonacciServiceTest {

    @ParameterizedTest
    @MethodSource("setup")
    void calc(int act, BigInteger exp) {
        assertThat(FibonacciService.calc(act), is(equalTo(exp)));
    }

    private static Stream<Arguments> setup() {
        return Stream.of(
                arguments(0, ZERO),
                arguments(1, ONE),
                arguments(8, new BigInteger("21")),
                arguments(12, new BigInteger("144")),
                arguments(666, new BigInteger("6859356963880484413875401302176431788073214234535725264860437720157972142108894511264898366145528622543082646626140527097739556699078708088"))
        );
    }
}
