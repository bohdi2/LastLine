package com.example;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;


public class LinesTest {
    @Rule
    public TemporaryFolder testFolder= new TemporaryFolder();

    @Test
    public void test_Basic_Functions() throws IOException {
        File file = testFolder.newFile("file.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write("hello\n");
        out.write("goodbye\n");
        out.close();

        Lines chunker = new Lines(file);

        Iterator<Long> ii = chunker.offsetIterator();

        assertTrue("hasNext", ii.hasNext());
        assertEquals(new Long(0), ii.next());

        assertTrue("hasNext", ii.hasNext());
        assertEquals(new Long(6), ii.next());

        assertFalse("EOF: " + ii, ii.hasNext());

    }

    @Test(expected=NoSuchElementException.class)

    public void test_Extra_Next() throws IOException {
        File file = testFolder.newFile("file.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write("hello\n");
        out.write("goodbye\n");
        out.close();


        Iterator<Long> ii = new Lines(file).offsetIterator();
        assertEquals(new Long(0), ii.next());
        assertEquals(new Long(6), ii.next());
        ii.next();
    }
}
