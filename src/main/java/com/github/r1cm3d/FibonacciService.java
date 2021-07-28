package com.github.r1cm3d;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

final class FibonacciService {

    private static final int CUTOFF = 1536;

    private FibonacciService() {
    }

    /**
     *  The Fibonacci sequence is defined as F(0)=0, F(1)=1, and F(n)=F(n−1)+F(n−2) for n≥2. So the sequence
     *  (starting with F(0)) is 0, 1, 1, 2, 3, 5, 8, 13, 21, ….
     *  This algorithm has an asymptotic complexity of Θ(logn) and it uses Karatsuba multiplication. This algorithm is
     *  pretty much the same found in {@link https://www.nayuki.io/page/fast-fibonacci-algorithms} with a couple of
     *  modifications as: removing comments and asserts.
     *
     * @param n the last number in the sequence F(n)=F(n−1)+F(n−2) for n>2
     * @return {@code f(n)}
     * @see https://www.nayuki.io/page/fast-fibonacci-algorithms
     * @see https://www.nayuki.io/page/karatsuba-multiplication
     */
    static BigInteger calc(int n) {
        BigInteger a = ZERO;
        BigInteger b = ONE;

        for (int bit = Integer.highestOneBit(n); bit != 0; bit >>>= 1) {
            BigInteger d = multiply(a, b.shiftLeft(1).subtract(a));
            BigInteger e = multiply(a, a).add(multiply(b, b));
            a = d;
            b = e;

            if ((n & bit) != 0) {
                BigInteger c = a.add(b);
                a = b;
                b = c;
            }
        }
        return a;
    }

    private static BigInteger multiply(BigInteger x, BigInteger y) {
        if (x.bitLength() <= CUTOFF || y.bitLength() <= CUTOFF)   // Base case
            return x.multiply(y);

        int n = Math.max(x.bitLength(), y.bitLength());
        int half = (n + 32) / 64 * 32;  // Number of bits to use for the low part
        BigInteger mask = ONE.shiftLeft(half).subtract(ONE);
        BigInteger xlow = x.and(mask);
        BigInteger ylow = y.and(mask);
        BigInteger xhigh = x.shiftRight(half);
        BigInteger yhigh = y.shiftRight(half);

        BigInteger a = multiply(xhigh, yhigh);
        BigInteger b = multiply(xlow.add(xhigh), ylow.add(yhigh));
        BigInteger c = multiply(xlow, ylow);
        BigInteger d = b.subtract(a).subtract(c);
        return a.shiftLeft(half).add(d).shiftLeft(half).add(c);
    }
}
