package com.brennan.jake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestBodyHolder {
    @Test
    @DisplayName("Create a BodyHolder")
    void ctorTest() {
        BodyHolder bh = new BodyHolder();
        assertNotNull(bh);
    }

    @Test
    @DisplayName("Is a BodyHolder")
    void idenTest() {
        BodyHolder bh = new BodyHolder();
        assertTrue(bh instanceof BodyHolder);
    }

    @Test
    @DisplayName("Default body is null")
    void noBodyTest() {
        BodyHolder bh = new BodyHolder();
        assertNull(bh.getBody());
    }

    @Test
    @DisplayName("Can update body")
    void updBodyTest() {
        BodyHolder bh = new BodyHolder();
        String newBody = "newBody";

        assertTrue(bh.setBody(newBody));
    }

    @Test
    @DisplayName("Body did update")
    void updBodyTest2() {
        BodyHolder bh = new BodyHolder();
        String newBody = "newBody";

        bh.setBody(newBody);

        assertEquals(newBody, bh.getBody());
    }
}
