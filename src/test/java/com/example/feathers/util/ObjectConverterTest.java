package com.example.feathers.util;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ObjectConverterTest {

    private final ObjectConverter objectConverter = new ObjectConverter();

    @Test
    public void testToPrimitives() {
        String test = "test input will be magic";
        Byte[] bigBytes = objectConverter.toObjects(test.getBytes());
        byte[] smallBytes = objectConverter.toPrimitives(bigBytes);

        assertEquals(test, new String(smallBytes, StandardCharsets.UTF_8));
    }


}