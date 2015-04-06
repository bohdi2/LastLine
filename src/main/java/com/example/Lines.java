package com.example;

import java.io.File;
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

    public Iterator<Long> reverseOffsetIterator() {
        return new ReverseOffsetIter();
    }


    class OffsetIter implements Iterator<Long> {
        private Iterator<ForwardChunk> m_chunks;
        private Iterator<Long> m_offsets;

        public OffsetIter() {
            // If file is empty then it should not have an initial offset of 0

            m_chunks = new ForwardFileChunker(m_file).iterator();

            if (0 == m_file.length()) {
                m_offsets = new EmptyIter<Long>();
            }
            else {
                m_offsets = new FirstIter();
            }
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

    }

    class ReverseOffsetIter implements Iterator<Long> {
        private Iterator<ReverseChunk> m_chunks;
        private Iterator<Long> m_offsets;

        public ReverseOffsetIter() {
            // If file is empty then it should not have an initial offset of 0

            m_chunks = new ReverseFileChunker(m_file).iterator();
            m_offsets = new EmptyIter<Long>();
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
            m_hasNext = false;
            return 0L;
        }
        public void remove() {
            throw new UnsupportedOperationException("remove not supported");
        }
    }


}
