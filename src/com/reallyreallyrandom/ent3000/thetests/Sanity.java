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

import java.util.BitSet;

import org.apache.commons.math3.stat.inference.ChiSquareTest;

public class Sanity implements ITestish {

    @Override
    public double getPValue(byte[] samples) {
        long trueCount = BitSet.valueOf(samples).cardinality();
        long falseCount = 8 * samples.length - trueCount;
        double bothExpected = 8 / 2 * samples.length;
        double[] expected = { bothExpected, bothExpected };
        long[] observed = { trueCount, falseCount };

        double p = new ChiSquareTest().chiSquareTest(expected, observed);

        return p;
    }
}
