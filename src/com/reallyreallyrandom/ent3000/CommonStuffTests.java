package com.reallyreallyrandom.ent3000;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CommonStuffTests {
    @Test
    void testReadCalibration() {
        CommonStuff cs = new CommonStuff();
        String json = cs.readFromJARFile("thetests/calibration.json");
        assertTrue(json.contains("paul.uszak (at) gmail.com"));
    }

    @Test
    void testReadHelp() {
        CommonStuff cs = new CommonStuff();
        String json = cs.readFromJARFile("help.txt");
        assertTrue(json.contains("John Walker"));
    }

    @ParameterizedTest
    @CsvSource({
            "entropy, 75_000, 7.9975",
            "compression, 900_000, 1_816_326"
    })
    void testWithGoodCsvSource(String test, int size, double testStatistic) {
        CommonStuff cs = new CommonStuff();
        double p = cs.getPValue(test, size, testStatistic);
        assertTrue(p > 0.05);
    }

    @ParameterizedTest
    @CsvSource({
            "entropy, 150_000, 6.9975",
            "compression, 800_000, 1_000_000"
    })
    void testWithBadCsvSource(String test, int size, double testStatistic) {
        CommonStuff cs = new CommonStuff();
        double p = cs.getPValue(test, size, testStatistic);
        assertTrue(p == -1); // FIXME How is this handled later? Is there a (p == -2) also?
    }

    @ParameterizedTest
    @CsvSource({
            "entropy, 1_000_000, 7.99981390",
            "compression, 75_000, 151_720"
    })
    void testWithEdgyCsvSource(String test, int size, double testStatistic) {
        CommonStuff cs = new CommonStuff();
        double p = cs.getPValue(test, size, testStatistic);
        assertTrue((p > 0.01) && (p < 0.99));
    }

    @ParameterizedTest
    @CsvSource({
            "-1.96",
            "1.96"
    })
    void testAlgabraicP(double Z) {
        CommonStuff cs = new CommonStuff();
        double p = cs.getPValueZ(Z);
        assertEquals(0.049996, p, 0.000001);
    }

    @Test
    void testAlgabraicP0() {
        CommonStuff cs = new CommonStuff();
        double p = cs.getPValueZ(0.0);
        assertEquals(1, p, 0.01);
    }

}
