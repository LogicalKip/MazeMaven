// Copyright 2003, William C. Wake. All rights reserved.

import java.util.Random;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAmazing {
    @Test
    public void testSeed0size15x20() {
        String expected =
                "Amazing - Copyright by Creative Computing, Morristown, NJ\n" +
                ":--:--:--:--:--:--:--:--:--:--:  :--:--:--:--:\n" +
                "I  I           I        I     I     I        I \n" +
                ":  :  :  :  :  :  :--:  :  :  :  :--:  :--:  :\n" +
                "I     I  I  I  I     I     I     I     I  I  I \n" +
                ":--:--:  :  :--:  :  :--:--:--:--:  :  :  :  :\n" +
                "I     I  I        I  I              I  I     I \n" +
                ":  :  :  :--:--:--:  :  :  :--:--:--:--:--:--:\n" +
                "I  I     I        I     I  I     I        I  I \n" +
                ":--:--:  :--:  :--:  :--:  :  :  :  :--:  :  :\n" +
                "I     I        I        I  I  I     I     I  I \n" +
                ":  :  :--:--:--:  :--:  :  :  :--:--:  :--:  :\n" +
                "I  I              I     I  I  I     I  I     I \n" +
                ":  :--:--:--:--:--:--:  :  :  :  :--:  :--:  :\n" +
                "I        I           I  I  I  I     I  I     I \n" +
                ":  :--:  :--:  :  :  :  :  :  :--:  :  :  :--:\n" +
                "I  I     I     I  I  I  I  I     I  I  I  I  I \n" +
                ":  :  :--:  :--:  :  :  :  :--:  :  :  :  :  :\n" +
                "I  I        I     I  I  I        I  I  I  I  I \n" +
                ":  :--:  :--:  :--:  :  :--:--:--:  :  :  :  :\n" +
                "I     I     I  I  I  I  I     I        I     I \n" +
                ":--:  :--:  :  :  :  :--:  :  :  :--:--:  :--:\n" +
                "I  I     I  I     I     I  I  I     I  I     I \n" +
                ":  :--:  :--:--:--:  :  :  :  :--:  :  :  :  :\n" +
                "I     I     I     I  I  I  I     I  I  I  I  I \n" +
                ":  :  :--:  :  :  :--:  :  :--:  :  :  :  :  :\n" +
                "I  I        I  I     I  I     I     I  I  I  I \n" +
                ":  :--:--:  :--:  :  :  :--:  :--:--:  :  :--:\n" +
                "I  I     I        I  I     I     I     I     I \n" +
                ":  :  :--:--:--:--:  :  :--:  :  :  :--:--:  :\n" +
                "I     I           I  I     I  I  I  I     I  I \n" +
                ":  :--:  :--:--:--:  :--:  :  :  :  :  :  :  :\n" +
                "I  I                 I     I  I  I     I  I  I \n" +
                ":  :  :--:--:--:--:--:  :--:  :--:  :--:  :  :\n" +
                "I  I  I  I           I     I     I  I     I  I \n" +
                ":  :  :  :  :--:  :  :--:  :--:  :  :  :--:  :\n" +
                "I  I  I     I     I  I  I     I     I  I     I \n" +
                ":--:  :--:--:  :  :  :  :  :  :--:--:  :--:  :\n" +
                "I     I        I  I  I  I  I  I     I        I \n" +
                ":  :--:  :--:--:  :  :  :  :  :--:  :  :--:--:\n" +
                "I        I        I     I  I        I        I \n" +
                ":--:--:--:--:--:--:--:--:  :--:--:--:--:--:--:\n";

        Amazing.random = new Random(0);
        Amazing.doIt(15, 20);

        assertEquals( expected, Amazing.result.toString());
    }
    @Test
    public void testSeed100size4x5() {
        String expected =
                "Amazing - Copyright by Creative Computing, Morristown, NJ\n" +
                ":--:--:  :--:\n" +
                "I     I     I \n" +
                ":  :--:  :  :\n" +
                "I  I     I  I \n" +
                ":  :  :--:  :\n" +
                "I  I  I     I \n" +
                ":  :  :  :  :\n" +
                "I     I  I  I \n" +
                ":  :--:  :  :\n" +
                "I  I  I  I  I \n" +
                ":--:--:  :--:\n";

        Amazing.random = new Random(100);
        Amazing.doIt(4, 5);
        assertEquals( expected, Amazing.result.toString());

    }
}
