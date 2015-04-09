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

    public FileIterator<Long> offsetIterator() throws IOException {
        if (0 != m_file.length()) {
            return new FirstIterator<Long>(0L, new OffsetIter());
        }
        else {
            return new EmptyIterator<Long>();
        }
    }

    public FileIterator<Long> reverseOffsetIterator() throws IOException {
        if (0 != m_file.length()) {
            return new LastIterator<Long>(new ReverseOffsetIter(), 0L);
        }
        else {
            return new EmptyIterator<Long>();
        }
    }

    public FileIterator<String> lineIterator() throws IOException {
        return lineIterator(-1);
    }

    public FileIterator<String> lineIterator(int trim) throws IOException {
        return new LineIterator(m_file, offsetIterator(), trim);
    }


    public FileIterator<String> reverseLineIterator() throws IOException {
        return reverseLineIterator(-1);
    }

    public FileIterator<String> reverseLineIterator(int trim) throws IOException {
        return new LineIterator(m_file, reverseOffsetIterator(), trim);
    }




    class OffsetIter implements FileIterator<Long> {
        private FileIterator<List<Long>> m_chunks;
        private Iterator<Long> m_offsets;

        public OffsetIter() throws IOException {
            m_chunks = ForwardFileChunker.create(m_file);
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

    class ReverseOffsetIter implements FileIterator<Long> {
        private FileIterator<List<Long>> m_chunks;
        private Iterator<Long> m_offsets;

        public ReverseOffsetIter() throws IOException {
            m_chunks = ReverseFileChunker.create(m_file);
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






}
