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

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

public class UnCorrelation implements ITestish {
    @Override
    public double getPValue(byte[] samples) {
        double[] dubSamplesA = new double[samples.length];
        for (int i = 0; i < samples.length; i++) {
            dubSamplesA[i] = samples[i] & 0xff;
        }

        double[] dubSamplesB = new double[samples.length];
        var temp = dubSamplesA[samples.length - 1];
        for (var i = samples.length - 1; i > 0; i--) {
            dubSamplesB[i] = dubSamplesA[i - 1];
        }
        dubSamplesB[0] = temp;

        RealMatrix rm = new Array2DRowRealMatrix(samples.length, 2);
        rm.setColumn(0, dubSamplesA);
        rm.setColumn(1, dubSamplesB);

        PearsonsCorrelation pc = new PearsonsCorrelation(rm);
        /*
         * From
         * https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/index.html:
         * -
         * Returns a matrix of p-values associated with the
         * (two-sided) null hypothesis that the corresponding
         * correlation coefficient is zero.
         */
        RealMatrix ps = pc.getCorrelationPValues();
        return ps.getEntry(0, 1);
    }

    @Override
    public double getPValue(int[] samples) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPValue'");
    }
}
