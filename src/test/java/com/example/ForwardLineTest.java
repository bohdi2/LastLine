package com.example;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;


public class ForwardLineTest {
    @Rule
    public TemporaryFolder testFolder= new TemporaryFolder();

    @Test
    public void test_Forward_Basic_Functions() throws IOException {
        assertLines(testFolder, "hello\ngoodbye\n", "hello", "goodbye");
    }

    @Test
    public void test_Forward_Abrust_Ending() throws IOException {
        assertLines(testFolder, "hello\ngoodbye", "hello", "goodbye");
    }

    @Test
    public void test_Forward_Empty_Lines() throws IOException {
        assertLines(testFolder, "\n\n", "", "");
    }

    @Test
    public void test_Forward_Empty_File() throws IOException {
        assertLines(testFolder, "");
    }

    @Test(expected=NoSuchElementException.class)
    public void test_Forward_Extra_Next() throws IOException {
        RandomAccessFile file = Helper.createFile(testFolder, "hello\n", "goodbye\n");

        Iterator<String> ii = new Lines(file).lineIterator();

        assertEquals("hello", ii.next());
        assertEquals("goodbye", ii.next());
        ii.next();
    }

    public static void assertLines(TemporaryFolder folder, String contents, String ... strings) throws IOException {
        RandomAccessFile file = Helper.createFile(folder, contents);

        Helper.assertContainsExactly(new Lines(file).lineIterator(), strings);
    }


}
