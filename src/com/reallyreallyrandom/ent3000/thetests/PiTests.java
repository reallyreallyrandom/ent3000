/*
Copyright (c) 2023 Paul Uszak.  Email: paul.uszak (at) gmail.com

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

// spell-checker: disable 

package com.reallyreallyrandom.ent3000.thetests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PiTests {
    @ParameterizedTest
    @ValueSource(ints = { 25_000, 50_000, 75_000, 100_000, 150_000, 200_000, 300_000, 400_000, 500_000, 600_000,
            700_000, 800_000, 900_000, 1_000_000 })
    void testGetPValues(int sizes) {
        RandomGenerator rng = new Random(1);
        int noSamples = sizes;
        byte[] samples = new byte[noSamples];
        rng.nextBytes(samples);

        ITestish test = new Pi();
        double p = test.getPValue(samples);
        assertTrue((p > 0.025) & (p < 0.95));
    }

    @Test
    void testGetPValue() {
        RandomGenerator rng = new Random(24);
        int noSamples = 50_000;
        byte[] samples = new byte[noSamples];
        rng.nextBytes(samples);

        ITestish test = new Pi();
        double p = test.getPValue(samples);
        assertTrue((p < 0.025) | (p > 0.95));
    }

}