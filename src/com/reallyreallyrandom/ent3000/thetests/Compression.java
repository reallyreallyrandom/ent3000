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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorOutputStream;

import com.reallyreallyrandom.ent3000.CommonStuff;

public class Compression implements ITestish {

    @Override
    public double getPValue(byte[] samples) {
        double p;
        int testStatistic;
        int bufferSize = (int) (samples.length * 2.4); // Allow 40% headroom.
        ByteArrayOutputStream bos = new ByteArrayOutputStream(bufferSize);
        try (BZip2CompressorOutputStream bzOut = new BZip2CompressorOutputStream(bos);
                LZMACompressorOutputStream lzOut = new LZMACompressorOutputStream(bos)) {
            bzOut.write(samples);
            bzOut.close();
            lzOut.write(samples);
            lzOut.close();

            testStatistic = bos.toByteArray().length;

            CommonStuff cs = new CommonStuff();
            p = cs.getPValue("compression", samples.length, testStatistic);

        } catch (IOException e) {
            p = -2; // TODO Implement p = -2 is a test code failure, not a statistical failure.
        }
        return p;
    }

    @Override
    public double getPValue(int[] samples) {
        throw new UnsupportedOperationException("Unimplemented method 'getPValue'");
    }
}
