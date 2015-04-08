package com.example.impl;

import com.example.FileIterator;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ReverseFileChunker  {
    private final int CHUNK_SIZE = 1024;

    private final File m_file;

    public ReverseFileChunker(File file) {
        m_file = file;
    }

    public FileIterator<List<Long>> iterator() {
        try {
            return new ReverseChunkIter(m_file);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class ReverseChunkIter implements FileIterator<List<Long>> {
        private final RandomAccessFile m_file;
        private final byte[] m_buffer;

        public ReverseChunkIter(File file) throws IOException {
            m_file = new RandomAccessFile(file, "r");
            m_file.seek(m_file.length());
            m_buffer = new byte[CHUNK_SIZE];
        }

        public boolean hasNext() {
            try {
                //System.err.format("ReverseFileChunker.hasNext() %d%n", nextChunkSize());
                return nextChunkSize() > 0;
            }
            catch (IOException e) {
                return false;
            }
        }

        public List<Long> next() {

            try {
                long position = m_file.getFilePointer();
                int size = nextChunkSize();

                //System.err.format("ReverseFileChunker.next(): position: %d, size: %d%n",
                //                  position,
                //                  size);

                m_file.seek(position - size);
                m_file.readFully(m_buffer, 0, size);
                m_file.seek(position - size);

                //System.err.format("ReverseFileChunker.next().exit: %d%n", m_file.getFilePointer());
                return Util.getReverseOffsets(m_file.length(), position-size, m_buffer, size);
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
        }

        private int nextChunkSize() throws IOException {
            long remaining = m_file.getFilePointer();

            if (remaining > CHUNK_SIZE)
                return CHUNK_SIZE;
            else
                return (int) remaining;
        }

    }

}
