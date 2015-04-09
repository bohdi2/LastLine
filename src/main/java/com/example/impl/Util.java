package com.example.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;


public class Util {


    public static List<Long> getOffsets(long position, byte[] buffer, int size) {
        List<Long> offsets = new ArrayList<Long>();

        for (int ii = 0; ii < size; ii++) {
            if ('\n' == buffer[ii] && (ii + 1 < size)) {
                offsets.add(position + ii + 1);
            }
        }

        return offsets;
    }

    public static List<Long> getReverseOffsets(long fileLength, long position, byte[] buffer, int size) {
        List<Long> offsets = new ArrayList<Long>();

        if (size > 0) {
            for (int ii = size - 1; ii >= 0; ii--) {
                if ('\n' == buffer[ii] && (fileLength != position + ii + 1)) {
                    offsets.add(position + ii + 1);
                }
            }
        }

        return offsets;
    }

    /**
     * Reads a line truncating it as needed.
     * Assumes that hasPrevious was previously called.
     */

    public static String readLine(RandomAccessFile file, long position, int trim) throws IOException {
        StringBuilder input = new StringBuilder();
        int c = -1;
        boolean eol = false;
        int count = 0;

        file.seek(position);

        while (!eol) {
            switch (c = file.read()) {
                case -1:
                case '\n':
                    eol = true;
                    break;
                case '\r':
                    eol = true;
                    long cur = file.getFilePointer();
                    if ((file.read()) != '\n') {
                        file.seek(cur);
                    }
                    break;
                default:
                    if (-1 != trim && count >= trim) {
                        eol = true;
                        break;
                    }
                    input.append((char) c);
                    count++;
                    break;
            }
        }

        if ((c == -1) && (input.length() == 0)) {
            return null;
        }

        return input.toString();
    }
}
