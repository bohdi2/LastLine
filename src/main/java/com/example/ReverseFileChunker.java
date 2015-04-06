package com.example;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.NoSuchElementException;

class ReverseFileChunker implements Iterable<ReverseChunk> {
    private final int CHUNK_SIZE = 1024;

    private final File m_file;

    public ReverseFileChunker(File file) {
        m_file = file;
    }

    public Iterator<ReverseChunk> iterator() {
        try {
            return new ChunkIter(m_file);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class ChunkIter implements Iterator<ReverseChunk> {
        private final RandomAccessFile m_file;
        private final byte[] m_buffer;

        public ChunkIter(File file) throws IOException {
            m_file = new RandomAccessFile(file, "r");
            m_file.seek(m_file.length());
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

        public ReverseChunk next() {

            try {
                long position = m_file.getFilePointer();
                int size = nextChunkSize();

                System.err.format("ReverseFileChunker: position: %d, size: %d%n",
                                  position,
                                  size);

                m_file.seek(position-size);
                m_file.readFully(m_buffer, 0, size);
                m_file.seek(position-size);

                return new ReverseChunk(position-size, m_buffer, size);
            }
            catch (IOException e) {
                throw new NoSuchElementException();
            }

        }

        public void remove() {
            throw new UnsupportedOperationException("remove not supported");
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
