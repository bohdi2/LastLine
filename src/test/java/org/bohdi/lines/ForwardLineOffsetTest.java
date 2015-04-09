package org.bohdi.lines;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;


public class ForwardLineOffsetTest {
    @Rule
    public TemporaryFolder testFolder= new TemporaryFolder();

    @Test
    public void test_Forward_Basic_Functions() throws IOException {
        assertOffsets(testFolder, "hello\ngoodbye\n", 0, 6);
    }

    @Test
    public void test_Forward_Abrust_Ending() throws IOException {
        assertOffsets(testFolder, "hello\ngoodbye", 0, 6);
    }

    @Test
    public void test_Forward_Empty_Lines() throws IOException {
        assertOffsets(testFolder, "\n\n", 0, 1);
    }

    @Test
    public void test_Forward_Empty_File() throws IOException {
        assertOffsets(testFolder, "");

    }

    @Test(expected=NoSuchElementException.class)
    public void test_Forward_Extra_Next() throws IOException {
        RandomAccessFile file = Helper.createFile(testFolder, "hello\n", "goodbye\n");

        Iterator<Long> ii = Lines.offsetIterator(file);
        assertEquals(new Long(0), ii.next());
        assertEquals(new Long(6), ii.next());
        ii.next();
    }

    public static void assertOffsets(TemporaryFolder folder, String contents, long ... longs) throws IOException {
        RandomAccessFile file = Helper.createFile(folder, contents);

        Helper.assertContainsExactly(Lines.offsetIterator(file), longs);
    }

}
