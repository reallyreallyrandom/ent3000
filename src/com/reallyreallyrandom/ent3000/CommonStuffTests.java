package com.reallyreallyrandom.ent3000;

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
            "pi, 50_000, 3.14159",
            "pi, 400_000, 3.14159",
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
            "pi, 500_000, 4.14159",
            "pi, 700_000, 2.14159",
            "entropy, 150_000, 6.9975",
            "compression, 800_000, 1_000_000"
    })
    void testWithBadCsvSource(String test, int size, double testStatistic) {
        CommonStuff cs = new CommonStuff();
        double p = cs.getPValue(test, size, testStatistic);
        assertTrue(p == -1);   // FIXME How is this handled later? Is there a (p == -2) also?
    }

    @ParameterizedTest
    @CsvSource({
            "pi, 100_000, 3.138",
            "pi, 700_000, 3.13993",
            "entropy, 1_000_000, 7.99981390",
            "compression, 75_000, 151_720"
    })
    void testWithEdgyCsvSource(String test, int size, double testStatistic) {
        CommonStuff cs = new CommonStuff();
        double p = cs.getPValue(test, size, testStatistic);
        assertTrue((p > 0.01) && (p < 0.99));
    }

}
