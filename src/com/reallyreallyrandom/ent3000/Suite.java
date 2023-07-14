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

package com.reallyreallyrandom.ent3000;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.random.RandomGenerator;

import com.reallyreallyrandom.ent3000.thetests.Chi;
import com.reallyreallyrandom.ent3000.thetests.Compression;
import com.reallyreallyrandom.ent3000.thetests.Entropy;
import com.reallyreallyrandom.ent3000.thetests.ITestish;
import com.reallyreallyrandom.ent3000.thetests.Mean;
import com.reallyreallyrandom.ent3000.thetests.Pi;
import com.reallyreallyrandom.ent3000.thetests.Sanity;
import com.reallyreallyrandom.ent3000.thetests.UnCorrelation;

public class Suite {

    final static double ALPHA = 0.05;
    final static double SANITY_ALPHA = 0.001;
    final static int[] ACCEPTABLE_SAMPLE_SIZES = { 25_000, 50_000, 75_000, 100_000, 150_000, 200_000, 300_000, 400_000,
            500_000, 600_000,
            700_000, 800_000, 900_000, 1_000_000 };

    // TODO Use library like https://picocli.info/ for arguments handling.
    public static void main(String[] args) {
        System.out.println("\nent3000 starting...");
        System.out.println("--help option to display this help.");
        String inFile = null;

        try {
            if (args.length > 0) {
                inFile = args[0];
                if (args[0].equals("--help")) {
                    CommonStuff cs = new CommonStuff();
                    String help = cs.readFromJARFile("help.txt");
                    System.out.println(help);
                    System.exit(0);
                }
            } else {
                inFile = "internal_CSPRNG";
            }
            Suite ent = new Suite();
            ent.runTests(inFile);
        } catch (Exception e) {
            System.err.println("Problem testing file: " + inFile);
            System.err.println(e.getMessage());
            System.err.println("Exit.");
        }
    }

    
    public void prettyPrintResult(String testName, double pValue, String testComment) {
        if (pValue == -1) {
            System.out.printf("%-15s  %s  %n", testName + ",", "OoC,          FAIL.");
        } else if (pValue == -2) {
            System.out.printf("%-15s  %s  %n", testName + ",", "Compression error.");
        } else {
            String pOutput = "p = " + String.format("%.3f", pValue) + ",  ";
            System.out.printf("%-15s  %s  %s  %n", testName + ",", pOutput, testComment);
        }
    }


    public byte[] truncate(byte[] array) {
        if (array.length < ACCEPTABLE_SAMPLE_SIZES[0]) {
            System.err.println("Sample file is too small.");
            System.err.println("Exit.");
            System.exit(-1);
        }

        byte[] truncated = null;
        for (int i = ACCEPTABLE_SAMPLE_SIZES.length - 1; i >= 0; i--) {
            int target = ACCEPTABLE_SAMPLE_SIZES[i];

            if (array.length >= target) {
                truncated = new byte[target];
                System.arraycopy(array, 0, truncated, 0, target);
                break;
            }
        }
        return truncated;
    }

    public void runTests(String filename) throws IOException, NoSuchAlgorithmException {
        // TODO Break this out into a method?
        byte[] samples;

        if (filename == "internal_CSPRNG") {
            RandomGenerator rng = SecureRandom.getInstance("NativePRNG");
            int noFakeSamples = ACCEPTABLE_SAMPLE_SIZES[rng.nextInt(ACCEPTABLE_SAMPLE_SIZES.length)];
            samples = new byte[noFakeSamples];
            rng.nextBytes(samples);
            System.out.println("Testing internal native CSPRNG.");
            System.out.println("Testing " + samples.length + " bytes.");
        } else {
            samples = Files.readAllBytes(Paths.get(filename));
            samples = truncate(samples);
            System.out.println("Testing first " + samples.length + " bytes.");
        }

        ITestish test;
        double pValue;
        String testComment = null;

        test = new Sanity();
        pValue = test.getPValue(samples);
        if (pValue > SANITY_ALPHA) {
            System.out.println("Sane sample file. Good.");
            System.out.println("------------------------------------");
        } else {
            System.out.println("Insane sample file. Bad.");
            System.out.println("Exit.");
            System.exit(-1);
        }

        test = new Entropy();
        pValue = test.getPValue(samples);
        if (pValue > ALPHA) {
            testComment = "PASS.";
        } else {
            testComment = "FAIL.";
        }
        prettyPrintResult("Entropy", pValue, testComment);

        test = new Compression();
        pValue = test.getPValue(samples);
        if (pValue > ALPHA) {
            testComment = "PASS.";
        } else {
            testComment = "FAIL.";
        }
        prettyPrintResult("Compression", pValue, testComment);

        test = new Chi();
        pValue = test.getPValue(samples);
        if (pValue > ALPHA) {
            testComment = "PASS.";
        } else {
            testComment = "FAIL.";
        }
        prettyPrintResult("Chi", pValue, testComment);

        test = new Mean();
        pValue = test.getPValue(samples);
        if (pValue > ALPHA) {
            testComment = "PASS.";
        } else {
            testComment = "FAIL.";
        }
        prettyPrintResult("Mean", pValue, testComment);

        test = new Pi();
        pValue = test.getPValue(samples);
        if (pValue > ALPHA) {
            testComment = "PASS.";
        } else {
            testComment = "FAIL.";
        }
        prettyPrintResult("Pi", pValue, testComment);

        test = new UnCorrelation();
        pValue = test.getPValue(samples);
        if (pValue > ALPHA) {
            testComment = "PASS.";
        } else {
            testComment = "FAIL.";
        }
        prettyPrintResult("UnCorrelation", pValue, testComment);

        System.out.println("------------------------------------");
        System.out.println("Finished.");
    }

}
