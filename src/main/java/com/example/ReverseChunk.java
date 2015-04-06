package com.example;

import java.util.*;

class ReverseChunk {
    private final List<Long> m_offsets;

    public ReverseChunk(long position, byte[] buffer, int size) {
        m_offsets = new ArrayList<Long>();

        if (size>0) {
            for (int ii = size - 1; ii >= 0; ii--) {
                if ('\n' == buffer[ii]) {
                    m_offsets.add(position + ii + 1);
                }
            }
        }

        System.err.format("ReverseChunk position: %d, Size: %d%n",
                          position,
                          size);

        System.err.format("ReverseChunk offsets: %s%n", m_offsets);

    }

    public Iterator<Long> iterator() {
        return m_offsets.iterator();
    }

    public String toString() {
        return String.format("%s", m_offsets);
    }
}