package com.example;

import com.example.impl.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class Lines {
    private final File m_file;

    public Lines(File file) {
        m_file = file;
    }

    public Iterator<Long> offsetIterator() {
        if (0 != m_file.length()) {
            return new FirstIterator<Long>(0L, new OffsetIter());
        }
        else {
            return new EmptyIterator<Long>();
        }
    }

    public Iterator<Long> reverseOffsetIterator() {
        if (0 != m_file.length()) {
            return new LastIterator<Long>(new ReverseOffsetIter(), 0L);
        }
        else {
            return new EmptyIterator<Long>();
        }
    }

    public Iterator<String> lineIterator() throws IOException {
        return lineIterator(-1);
    }

    public Iterator<String> lineIterator(int trim) throws IOException {
        return new LineIterator(m_file, offsetIterator(), trim);
    }


    public Iterator<String> reverseLineIterator() throws IOException {
        return reverseLineIterator(-1);
    }

    public Iterator<String> reverseLineIterator(int trim) throws IOException {
        return new LineIterator(m_file, reverseOffsetIterator(), trim);
    }




    class OffsetIter implements Iterator<Long> {
        private Iterator<List<Long>> m_chunks;
        private Iterator<Long> m_offsets;

        public OffsetIter() {
            m_chunks = new ForwardFileChunker(m_file).iterator();
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
            //m_chunks.close();
        }

    }

    class ReverseOffsetIter implements Iterator<Long> {
        private Iterator<List<Long>> m_chunks;
        private Iterator<Long> m_offsets;

        public ReverseOffsetIter() {
            m_chunks = new ReverseFileChunker(m_file).iterator();
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

        public void close() {
            //m_chunks.close();
        }
    }






}
