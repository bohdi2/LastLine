package com.example;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.NoSuchElementException;

class Lines {
    private final int CHUNK_SIZE = 1024;

    private final byte m_buffer[];

    private final File m_file;

    public Lines(File file) {
        m_file = file;
        m_buffer = new byte[CHUNK_SIZE];
    }

    public Iterator<Long> offsetIterator() {
        return new OffsetIter();
    }

    class OffsetIter implements Iterator<Long> {
        private Iterator<ForwardChunk> chunks = new ForwardFileChunker(m_file).iterator();
        private Iterator<Long> offsets = new FirstIter();

        public boolean hasNext() {
            return chunks.hasNext() || offsets.hasNext();
        }

        public Long next() {
            if (! hasNext())
                throw new NoSuchElementException();

            while (! offsets.hasNext())
                offsets = chunks.next().iterator();

            return offsets.next();
        }

        public void remove() {
            throw new UnsupportedOperationException("remove not supported");
        }

    }

    class EmptyIter<T> implements Iterator<T> {
        public boolean hasNext() {
            return false;
        }
        public T next() {
            return null;
        }
        public void remove() {
            throw new UnsupportedOperationException("remove not supported");
        }
    }

    class FirstIter implements Iterator<Long> {
        private boolean m_hasNext = true;

        public boolean hasNext() {
            return m_hasNext;
        }
        public Long next() {
            System.err.println("FirstIter.next");
            m_hasNext = false;
            return 0L;
        }
        public void remove() {
            throw new UnsupportedOperationException("remove not supported");
        }
    }


}
