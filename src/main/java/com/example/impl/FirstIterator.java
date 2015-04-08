package com.example.impl;

import java.util.Iterator;

public class FirstIterator<T> implements Iterator<T> {
    private boolean m_hasFirst = true;
    private final T m_first;
    private final Iterator<T> m_tail;

    public FirstIterator(T first, Iterator<T> tail) {
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
}