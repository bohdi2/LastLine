
package com.example;
 
import java.io.*;

/*
* Reads a random file backwards searching for '\n' character. When it finds one
* it then moves forward and reads the line, truncating if needed.
*
* This is very special reverse reader and tailored to LogServer's needs.
 */

public class ReverseChunk {
    private final int CHUNK_SIZE = 1024;
    private final RandomAccessFile m_file;
    private long m_filePosition;
    private int m_bufferPosition;
    private final byte m_buffer[];

    public ReverseChunk(RandomAccessFile file) throws IOException {
        m_file = file;
        m_filePosition = m_file.length() - 1;
        m_file.seek(m_filePosition);
        m_bufferPosition = -1;
        m_buffer = new byte[CHUNK_SIZE];
    }

    public void close() throws IOException {
        m_file.close();
    }

    public String toString() {
        return String.format("FilePos: %d, ChunkPos: %d",
                                m_filePosition,
                             m_bufferPosition);
    }

    // Return true if there is data remaining in the buffer or file.

    private boolean hasDataRemaining() {
        return (-1 != m_bufferPosition || nextChunkSize() > 0);
    }

    /**
    * Searches backwards for '\n' character.
    * returns true if it finds one.
     * */

     private boolean hasPrevious() throws IOException {
         boolean notEmpty = hasDataRemaining();

        while (hasDataRemaining()) {
            if (-1 == m_bufferPosition) {
                readChunk(nextChunkSize());
            }

            // Look fore '\n' in buffer
            while (m_bufferPosition > -1 && '\n' != m_buffer[m_bufferPosition--])
                ;

            // If we found '\n' we can stop looking.
            if (-1 != m_bufferPosition)
                return true;
        }
        return notEmpty;
    }

    /**
     * Reads a line truncating it as needed.
     * Assumes that hasPrevious was previously called.
     */

    public String readLine(int trim) throws IOException {
        if (!hasPrevious()) //-1 == m_bufferPosition)
            return null;

        long cur = m_file.getFilePointer();
        m_file.seek(getLinePosition());
        String line = readLine0(trim);
        m_file.seek(cur);
        return line;
    }

    public String readLine0(int trim) throws IOException {
        StringBuffer input = new StringBuffer();
        int c = -1;
        boolean eol = false;
        int count = 0;

        while (!eol) {
            switch (c = m_file.read()) {
                case -1:
                case '\n':
                    eol = true;
                    break;
                case '\r':
                    eol = true;
                    long cur = m_file.getFilePointer();
                    if ((m_file.read()) != '\n') {
                        m_file.seek(cur);
                    }
                    break;
                default:
                    if (-1 != trim && count >= trim) {
                        eol = true;
                        break;
                    }
                    input.append((char)c);
                    count++;
                    break;
            }
        }

        if ((c == -1) && (input.length() == 0)) {
            return null;
        }
        return input.toString();
    }

    private long getLinePosition() {
        if (-1 == m_bufferPosition)
            return m_filePosition;
        else
            return m_filePosition + m_bufferPosition + 2;
    }


    private int nextChunkSize() {
        if (m_filePosition > CHUNK_SIZE)
            return CHUNK_SIZE;
        else
            return (int) m_filePosition;
    }
 
    private void readChunk(int size) throws IOException {
        if (size > 0) {
            m_file.seek(m_filePosition - size);
            m_file.readFully(m_buffer, 0, size);
            m_filePosition -= size;
            m_bufferPosition = size - 1;
        } else {
            m_bufferPosition = -1;
        }
    }
 
 
}
 
 
