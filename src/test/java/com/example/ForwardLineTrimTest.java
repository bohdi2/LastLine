package com.example;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;


public class ForwardLineTrimTest {
    @Rule
    public TemporaryFolder testFolder= new TemporaryFolder();

    @Test
    public void test_Forward_Basic_Functions() throws IOException {
        assertTrimLines(testFolder, "hello\ngoodbye\n", "hel", "goo");
    }

    @Test
    public void test_Forward_Empty_Lines() throws IOException {
        assertTrimLines(testFolder, "\n\n", "", "");
    }

    @Test
    public void test_Forward_Empty_File() throws IOException {
        assertTrimLines(testFolder, "");

    }


    public static void assertTrimLines(TemporaryFolder folder, String contents, String ... strings) throws IOException {
        RandomAccessFile file = Helper.createFile(folder, contents);

        Helper.assertContainsExactly(new Lines(file).lineIterator(3), strings);
    }

}
