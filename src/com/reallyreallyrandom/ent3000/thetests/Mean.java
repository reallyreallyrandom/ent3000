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

package com.reallyreallyrandom.ent3000.thetests;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import com.reallyreallyrandom.ent3000.CommonStuff;

public class Mean implements ITestish {

    // Calculation from: https://math.stackexchange.com/a/4718129/394771
    @Override
    public double getPValue(byte[] samples) {
        SummaryStatistics ss = new SummaryStatistics();
        for (var b : samples) {
            ss.addValue(b & 0xff);
        }
        var testStatistic = ss.getMean();
        var stdDev = Math.sqrt((256 * 256 - 1.0) / (12.0 * samples.length));
        var Z = (testStatistic - 127.5) / stdDev;
        CommonStuff cs = new CommonStuff();
        double p = cs.getPValueZ(Z);

        return p;
    }

    @Override
    public double getPValue(int[] samples) {
        throw new UnsupportedOperationException("Unimplemented method 'getPValue'");
    }
}
