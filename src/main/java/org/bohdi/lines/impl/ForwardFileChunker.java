package org.bohdi.lines.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ForwardFileChunker implements Iterator<List<Long>> {
    private final int CHUNK_SIZE = 1024;

    private final RandomAccessFile m_file;
    private long m_filePosition;
    private final byte[] m_buffer;

    public ForwardFileChunker(RandomAccessFile file) {
        m_file = file;
        m_filePosition = 0;
        m_buffer = new byte[CHUNK_SIZE];
    }

    public boolean hasNext() {
        try {
            return nextChunkSize() > 0;
        } catch (IOException e) {
            return false;
        }
    }

    public List<Long> next() {

        try {
            long position = m_filePosition;
            int size = nextChunkSize();

            m_file.seek(m_filePosition);
            m_file.readFully(m_buffer, 0, size);
            m_filePosition += size;

            return Util.getOffsets(position, m_buffer, size);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            throw new NoSuchElementException();
        }

    }

    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }

    private int nextChunkSize() throws IOException {
        long remaining = m_file.length() - m_filePosition;

        if (remaining > CHUNK_SIZE)
            return CHUNK_SIZE;
        else
            return (int) remaining;
    }

}

