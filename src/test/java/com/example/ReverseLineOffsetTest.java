package com.example;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;


public class ReverseLineOffsetTest {
    @Rule
    public TemporaryFolder testFolder= new TemporaryFolder();


    // Reverse Iterators

    @Test
    public void test_Reverse_Basic_Functions() throws IOException {
        assertReverseOffsets(testFolder, "hello\ngoodby\n", 6, 0);
    }

    @Test
    public void test_Reverse_Abrust_Ending() throws IOException {
        assertReverseOffsets(testFolder, "hello\ngoodby", 6, 0);
    }

    @Test
    public void test_Reverse_Empty_Lines() throws IOException {
        assertReverseOffsets(testFolder, "\n\n", 1, 0);
    }

    @Test
    public void test_Reverse_Empty_File() throws IOException {
        assertReverseOffsets(testFolder, "");
    }

    @Test(expected=NoSuchElementException.class)
    public void test_Reverse_Extra_Next() throws IOException {
        File file = Helper.createFile(testFolder, "hello\n", "goodbye\n");

        Iterator<Long> ii = new Lines(file).reverseOffsetIterator();
        assertEquals(new Long(6), ii.next());
        assertEquals(new Long(0), ii.next());
        ii.next();
    }

    public static void assertReverseOffsets(TemporaryFolder folder, String contents, long ... longs) throws IOException {
        File file = Helper.createFile(folder, contents);

        Helper.assertContainsExactly(new Lines(file).reverseOffsetIterator(), longs);
    }
}
