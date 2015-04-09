package com.example.impl;

import com.example.FileIterator;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ReverseOffsetIterator implements FileIterator<Long> {
    private FileIterator<List<Long>> m_chunks;
    private Iterator<Long> m_offsets;

    public ReverseOffsetIterator(RandomAccessFile file) throws IOException {
        m_chunks = new ReverseFileChunker(file);
        m_offsets = new EmptyIterator<Long>();
    }

    public boolean hasNext() {
        return m_chunks.hasNext() || m_offsets.hasNext();
    }

    public Long next() {
        if (! hasNext())
            throw new NoSuchElementException();

        while (! m_offsets.hasNext())
            m_offsets = m_chunks.next().iterator();

        return m_offsets.next();
    }

    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }

    public void close() throws IOException {
        m_chunks.close();
    }
}