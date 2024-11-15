package com.brennan.jake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestUrlSafeConverter {
    @Test
    @DisplayName("Can convert safely")
    void stringConvertTest() {
        String unsafe = "Testing; very fun!";

        String expected = "Testing%3B%20very%20fun!";

        assertEquals(expected, UrlSafeConverter.convertToUrlSafe(unsafe));
    }

    @Test
    @DisplayName("Can process long arrays")
    void longArrConvertTest() {
        Long[] ints = {20L, 32L, 872L};

        String expected = "20,32,872";

        assertEquals(expected, UrlSafeConverter.longArrayParse(ints));
    }
}
