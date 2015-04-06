package com.example;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.Iterator;

import static org.junit.Assert.*;


public class ForwardFileChunkerTest {
    @Rule
    public TemporaryFolder testFolder= new TemporaryFolder();

    @Test
    public void test_Basic_Functions() throws IOException {
        File file = testFolder.newFile("file.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write("hello\n");
        out.write("goodbye\n");
        out.close();

        RandomAccessFile rfile = new RandomAccessFile(file, "rw");

        ForwardFileChunker chunker = new ForwardFileChunker(file);
/*
        Iterator<Long> ii = chunker.iterator();

        assertTrue("hasNext", ii.hasNext());
        assertEquals(new Long(0), ii.next());

        assertTrue("hasNext", ii.hasNext());
        assertEquals(new Long(6), ii.next());

        assertFalse(ii.hasNext());
*/
    }
}
