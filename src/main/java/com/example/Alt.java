
package com.example;
 
import java.io.*;
 
public class Alt {
    private final int m_trim;
    private final ReverseChunk m_chunk;
 
 
    public Alt(File file, int trim) throws IOException {
        m_trim = trim;
 
        m_chunk = new ReverseChunk(new RandomAccessFile(file, "r"));
    }

    public void close() throws IOException {
        m_chunk.close();
    }
 
    public String readLine() throws IOException {
           return m_chunk.readLine(m_trim);
    }
}
 
 
 
