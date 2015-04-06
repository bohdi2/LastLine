package com.example;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReverseLineTest {
    @Rule
    public TemporaryFolder testFolder= new TemporaryFolder();

    @Test
    public void test_Reverse_Basic_Functions() throws IOException {
        File file = testFolder.newFile("file.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write("hello\n");
        out.write("goodbye\n");
        out.close();

        Iterator<String> ii = new Lines(file).reverseLineIterator();

        assertTrue("hasNext", ii.hasNext());
        assertEquals("goodbye", ii.next());

        assertTrue("hasNext", ii.hasNext());
        assertEquals("hello", ii.next());

        assertFalse("EOF: " + ii, ii.hasNext());
    }

    @Test
    public void test_Reverse_Abrust_Ending() throws IOException {
        File file = testFolder.newFile("file.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write("hello\n");
        out.write("goodbye");
        out.close();

        Iterator<String> ii = new Lines(file).reverseLineIterator();

        assertTrue("hasNext", ii.hasNext());
        assertEquals("goodbye", ii.next());

        assertTrue("hasNext", ii.hasNext());
        assertEquals("hello", ii.next());

        assertFalse("EOF: " + ii, ii.hasNext());
    }

    @Test
    public void test_Reverse_Empty_Lines() throws IOException {
        File file = testFolder.newFile("file.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write("\n\n");
        out.close();

        Iterator<String> ii = new Lines(file).reverseLineIterator();

        assertTrue("hasNext", ii.hasNext());
        assertEquals("", ii.next());

        assertTrue("hasNext", ii.hasNext());
        assertEquals("", ii.next());

        assertFalse("EOF: " + ii, ii.hasNext());
    }

    @Test
    public void test_Reverse_Empty_File() throws IOException {
        File file = testFolder.newFile("file.txt");

        Iterator<String> ii = new Lines(file).reverseLineIterator();

        assertFalse("hasNext", ii.hasNext());
    }

    @Test(expected=NoSuchElementException.class)
    public void test_Reverse_Extra_Next() throws IOException {
        File file = testFolder.newFile("file.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write("hello\n");
        out.write("goodbye\n");
        out.close();

        Iterator<String> ii = new Lines(file).reverseLineIterator();

        assertEquals("goodbye", ii.next());
        assertEquals("hello", ii.next());
        ii.next();
    }



}