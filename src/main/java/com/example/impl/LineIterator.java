package com.example.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LineIterator implements Iterator<String> {
    private final RandomAccessFile m_file;
    private final Iterator<Long> m_offsets;
    private final int m_trim;

    public LineIterator(File file, Iterator<Long> offsets, int trim) throws IOException {
        m_file = new RandomAccessFile(file, "r");
        m_offsets = offsets;
        m_trim = trim;

    }
    public boolean hasNext() {
        return m_offsets.hasNext();
    }

    public String next() {
        try {
            long offset = m_offsets.next();
            return Util.readLine(m_file, offset, m_trim);
        }
        catch (IOException e) {
            throw new NoSuchElementException();
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }
}
