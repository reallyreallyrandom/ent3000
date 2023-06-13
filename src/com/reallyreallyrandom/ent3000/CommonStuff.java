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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CommonStuff {
    final static int NO_CALIBRATION_POINTS = 25;

    public String readFromJARFile(String filename) {
        StringBuffer sb = null;

        try {
            InputStream is = getClass().getResourceAsStream(filename);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            sb = new StringBuffer();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n"); // FIXME Adds unnecessary returns to the calibration file, but needed for the
                                 // help file.
            }

            br.close();
            isr.close();
            is.close();
        } catch (Exception e) {
            System.err.println("Cannot load file " + filename);
            System.err.println("Fatal. Exiting...");
            System.exit(-1);
        }
        return sb.toString();
    }

    public double getPValue(String test, int size, double testStatistic) {
        // FIXME Can this be written any neater?
        // See https://www.geeksforgeeks.org/parse-json-java/
        // TODO Can we automatically read the number of calibration points in the cal
        // file?
        String json = readFromJARFile("thetests/calibration.json");
        JSONParser parser = new JSONParser();
        double p = 0;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            String testType = jsonObject.get(test).toString();
            jsonObject = (JSONObject) parser.parse(testType);
            String sampleSize = jsonObject.get(String.valueOf(size)).toString();
            jsonObject = (JSONObject) parser.parse(sampleSize);

            JSONArray xJValues = (JSONArray) jsonObject.get("ts");
            JSONArray yJValues = (JSONArray) jsonObject.get("p");

            double[] x = new double[NO_CALIBRATION_POINTS];
            double[] y = new double[NO_CALIBRATION_POINTS];
            for (var i = 0; i < NO_CALIBRATION_POINTS; i++) {
                x[i] = (double) xJValues.get(i);
                y[i] = (double) yJValues.get(i);
            }

            /*
             * Check for in /out of calibration range.
             * Calibration range : 0.01 > p > 0.99
             * p = -1 means out of calibration range.
             */
            if ((testStatistic < x[0]) || (testStatistic > x[NO_CALIBRATION_POINTS - 1])) {
                p = -1;
            } else {
                SplineInterpolator interpolator = new SplineInterpolator();
                PolynomialSplineFunction funct = interpolator.interpolate(x, y);
                p = funct.value(testStatistic);
            }

        } catch (ParseException e) {
            System.err.println("Problem parsing calibration data file.");
            System.err.println("Fatal. Exiting...");
            System.exit(-1);
        }

        return p;
    }

}
