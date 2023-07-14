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

import java.util.Arrays;
import java.util.BitSet;

import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.inference.ChiSquareTest;

public class Chi implements ITestish {

    @Override
    public double getPValue(byte[] samples) {
        /*
         * Find no of categories to test for assuming a
         * minimum count of 34 per bin.
         */
        int SCAN_WINDOW_BITS;
        int l = samples.length;
        if (l > 974_848) {
            SCAN_WINDOW_BITS = 14;
        } else if (l > 452_608) {
            SCAN_WINDOW_BITS = 13;
        } else if (l > 208_896) {
            SCAN_WINDOW_BITS = 12;
        } else if (l > 95_744) {
            SCAN_WINDOW_BITS = 11;
        } else if (l > 43_520) {
            SCAN_WINDOW_BITS = 10;
        } else {
            SCAN_WINDOW_BITS = 9;
        }

        int categories = (int) Math.pow(2, SCAN_WINDOW_BITS);
        int no_windows = samples.length * 8 / SCAN_WINDOW_BITS;
        double[] expected = new double[categories];
        Arrays.fill(expected, 8.0 * samples.length / (SCAN_WINDOW_BITS * categories));
        BitSet bitSet = BitSet.valueOf(samples);
        Frequency freq = new Frequency();

        for (int i = 0; i < (no_windows * SCAN_WINDOW_BITS); i += SCAN_WINDOW_BITS) {
            int sum = 0;
            int bitValue = 0;
            for (int bitIndex = i; bitIndex < (i + SCAN_WINDOW_BITS); bitIndex++) {
                if (bitSet.get(bitIndex)) {
                    sum |= (1 << bitValue);
                }
                bitValue++;
            }
            freq.addValue(sum);
        }

        long[] observed = new long[categories];
        for (int i = 0; i < categories; i++) {
            observed[i] = freq.getCount(i);
        }

        double p = new ChiSquareTest().chiSquareTest(expected, observed);
        return p;
    }
}
