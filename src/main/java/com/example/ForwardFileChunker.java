package com.example;

import com.example.impl.Util;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class ForwardFileChunker implements Iterable<List<Long>> {
    private final int CHUNK_SIZE = 1024;

    private final File m_file;

    public ForwardFileChunker(File file) {
        m_file = file;
    }

    public Iterator<List<Long>> iterator() {
        try {
            return new ChunkIter(m_file);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class ChunkIter implements Iterator<List<Long>> {
        private final RandomAccessFile m_file;
        private final byte[] m_buffer;

        public ChunkIter(File file) throws IOException {
            m_file = new RandomAccessFile(file, "r");
            m_buffer = new byte[CHUNK_SIZE];
        }

        public boolean hasNext() {
            try {
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

                m_file.readFully(m_buffer, 0, size);
                return Util.getOffsets(position, m_buffer, size);
            }
            catch (IOException e) {
                throw new NoSuchElementException();
            }

        }

        public void remove() {
            throw new UnsupportedOperationException("remove not supported");
        }

        private int nextChunkSize() throws IOException {
            long remaining = m_file.length() - m_file.getFilePointer();

            if (remaining > CHUNK_SIZE)
                return CHUNK_SIZE;
            else
                return (int) remaining;
        }

    }

}
