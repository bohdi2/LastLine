package com.example;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;


public class ReverseLineTest {
    @Rule
    public TemporaryFolder testFolder= new TemporaryFolder();

    @Test
    public void test_Reverse_Basic_Functions() throws IOException {
        assertReverseLines(testFolder, "hello\ngoodbye\n", "goodbye", "hello");
    }

    @Test
    public void test_Reverse_Abrust_Ending() throws IOException {
        assertReverseLines(testFolder, "hello\ngoodbye", "goodbye", "hello");
    }

    @Test
    public void test_Reverse_Empty_Lines() throws IOException {
        assertReverseLines(testFolder, "\n\n", "", "");
    }

    @Test
    public void test_Reverse_Empty_File() throws IOException {
        assertReverseLines(testFolder, "");
    }

    @Test(expected=NoSuchElementException.class)
    public void test_Reverse_Extra_Next() throws IOException {
        File file = Helper.createFile(testFolder, "hello\n", "goodbye\n");

        Iterator<String> ii = new Lines(file).reverseLineIterator();

        assertEquals("goodbye", ii.next());
        assertEquals("hello", ii.next());

        ii.next();
    }

    public static void assertReverseLines(TemporaryFolder folder, String contents, String ... strings) throws IOException {
        File file = Helper.createFile(folder, contents);

        Helper.assertContainsExactly(new Lines(file).reverseLineIterator(), strings);
    }

}