package com.example.impl;

import com.example.FileIterator;

import java.io.IOException;

public class FirstIterator<T> implements FileIterator<T> {
    private boolean m_hasFirst = true;
    private final T m_first;
    private final FileIterator<T> m_tail;

    public FirstIterator(T first, FileIterator<T> tail) {
        m_first = first;
        m_tail = tail;
    }

    public boolean hasNext() {
        return m_hasFirst || m_tail.hasNext();
    }

    public T next() {
        if (m_hasFirst) {
            m_hasFirst = false;
            return m_first;
        }
        else {
            return m_tail.next();
        }
    }
    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }

    public void close() throws IOException {
        m_tail.close();
    }
}