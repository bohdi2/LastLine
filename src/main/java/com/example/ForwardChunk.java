package com.example;

import java.util.*;

class ForwardChunk {
    private final List<Long> m_offsets;

    public ForwardChunk(long position, byte[] buffer, int size) {
        m_offsets = new ArrayList<Long>();

        for (int ii=0; ii<size; ii++) {
            if ('\n' == buffer[ii] && (ii + 1 < size)) {
                m_offsets.add(position + ii + 1);
            }
        }
    }

    public Iterator<Long> iterator() {
        return m_offsets.iterator();
    }

    public String toString() {
        return String.format("%s", m_offsets);
    }
}