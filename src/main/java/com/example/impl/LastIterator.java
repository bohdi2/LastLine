package com.example.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LastIterator implements Iterator<Long> {
    private boolean m_hasLast = true;
    private Iterator<Long> m_head;

    public LastIterator(Iterator<Long> head) {
        m_head = head;
    }

    public boolean hasNext() {
        return m_hasLast;
    }

    public Long next() {
        if (m_head.hasNext()) {
            return m_head.next();
        }
        else if (m_hasLast) {
            m_hasLast = false;
            return 0L;
        }
        else {
            throw new NoSuchElementException();
        }

    }
    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }
}
