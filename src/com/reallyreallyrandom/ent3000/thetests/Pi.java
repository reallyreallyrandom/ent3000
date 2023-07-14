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

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import com.reallyreallyrandom.ent3000.CommonStuff;

public class Pi implements ITestish {

    // From:
    // https://math.stackexchange.com/questions/3518748/variance-of-area-and-average-estimators-in-monte-carlo-estimation-of-pi
    @Override
    public double getPValue(byte[] samples) {
        SummaryStatistics summaryys = new SummaryStatistics();
        for (int i = 0; i < samples.length; i = i + 4) {
            long x = ((long) (samples[i + 3] & 0xffL) << 24 |
                    (long) (samples[i + 2] & 0xffL) << 16 |
                    (long) (samples[i + 1] & 0xffL) << 8 |
                    (long) (samples[i + 0] & 0xffL) << 0);
            double normX = x / (Math.pow(256, 4) - 1D);
            double y = Math.sqrt(1 - (Math.pow(normX, 2)));
            summaryys.addValue(y);
        }
        double testStatistic = 4 * summaryys.getMean();

        var n = summaryys.getN();
        var variance = 16D / n * (2D / 3D - Math.pow(Math.PI / 4, 2));
        var stdDev = Math.sqrt(variance);
        var Z = (testStatistic - Math.PI) / stdDev;
        CommonStuff cs = new CommonStuff();
        double p = cs.getPValueZ(Z);

        return p;
    }
}
