package com.example;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;


public class ForwardChunkTest {

    @Test
    public void test_Basic_Functions() throws IOException {
        byte[] buffer = new byte[] {0x01, 0x02, 0x04, 0x08, 0x10};
        ForwardChunk chunk = new ForwardChunk(12, buffer, 5);

        assertState(chunk, 12, false);
        //assertEquals(0x01, chunk.read());

        assertState(chunk, 13, false);
        //assertEquals(0x02, chunk.read());

        assertState(chunk, 14, false);
        //assertEquals(0x04, chunk.read());

        assertState(chunk, 15, false);
        //assertEquals(0x08, chunk.read());

        assertState(chunk, 16, false);
        //assertEquals(0x10, chunk.read());

        assertState(chunk, 17, true);
    }

    private void assertState(ForwardChunk chunk, long expectedPosition, boolean expectedIsConsumed) {
        //assertEquals("Position", expectedPosition, chunk.getCursorPosition());
        //assertEquals("isConsumed", expectedIsConsumed, chunk.isConsumed());
    }
}
