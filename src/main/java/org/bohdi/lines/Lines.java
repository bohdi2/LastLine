package org.bohdi.lines;

import org.bohdi.lines.impl.*;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;


class Lines {

    public static Iterator<Long> offsetIterator(RandomAccessFile file) throws IOException {
        if (0 != file.length()) {
            return new FirstIterator<Long>(0L, new OffsetIterator(file));
        }
        else {
            return new EmptyIterator<Long>();
        }
    }

    public static Iterator<Long> reverseOffsetIterator(RandomAccessFile file) throws IOException {
        if (0 != file.length()) {
            return new LastIterator<Long>(new ReverseOffsetIterator(file), 0L);
        }
        else {
            return new EmptyIterator<Long>();
        }
    }

    public static Iterator<String> lineIterator(RandomAccessFile file) throws IOException {
        return lineIterator(file, -1);
    }

    public static Iterator<String> lineIterator(RandomAccessFile file, int trim) throws IOException {
        return new LineIterator(file, offsetIterator(file), trim);
    }


    public static Iterator<String> reverseLineIterator(RandomAccessFile file) throws IOException {
        return reverseLineIterator(file, -1);
    }

    public static Iterator<String> reverseLineIterator(RandomAccessFile file, int trim) throws IOException {
        return new LineIterator(file, reverseOffsetIterator(file), trim);
    }

}
