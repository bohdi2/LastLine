package org.bohdi.lines.impl;

import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class OffsetIterator implements Iterator<Long> {
    private Iterator<List<Long>> m_chunks;
    private Iterator<Long> m_offsets;

    public OffsetIterator(RandomAccessFile file) {
        m_chunks = new ForwardFileChunker(file);
        m_offsets = new EmptyIterator<Long>();
    }

    public boolean hasNext() {
        return m_chunks.hasNext() || m_offsets.hasNext();
    }

    public Long next() {
        if (! hasNext())
            throw new NoSuchElementException();

        while (! m_offsets.hasNext()) {
            m_offsets = m_chunks.next().iterator();
        }

        return m_offsets.next();
    }

    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }

}
