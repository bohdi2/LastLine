package com.example.impl;

import java.util.Iterator;

public class FirstIterator implements Iterator<Long> {
    private boolean m_hasFirst = true;
    private Iterator<Long> m_tail;

    public FirstIterator(Iterator<Long> tail) {
        m_tail = tail;
    }

    public boolean hasNext() {
        return m_hasFirst || m_tail.hasNext();
    }

    public Long next() {
        if (m_hasFirst) {
            m_hasFirst = false;
            return 0L;
        }
        else {
            return m_tail.next();
        }
    }
    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }
}