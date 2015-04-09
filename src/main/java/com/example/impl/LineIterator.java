package com.example.impl;

import com.example.FileIterator;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.NoSuchElementException;

public class LineIterator implements FileIterator<String> {
    private final RandomAccessFile m_file;
    private final FileIterator<Long> m_offsets;
    private final int m_trim;

    public LineIterator(RandomAccessFile file, FileIterator<Long> offsets, int trim) throws IOException {
        m_file = file;
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

    public void close() throws IOException {
        m_file.close();
        m_offsets.close();
    }
}
