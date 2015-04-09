package org.bohdi.lines.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LastIterator<T> implements Iterator<T> {
    private boolean m_hasLast = true;
    private final T m_last;
    private final Iterator<T> m_head;

    public LastIterator(Iterator<T> head, T last) {
        m_last = last;
        m_head = head;
    }

    public boolean hasNext() {
        return m_hasLast;
    }

    public T next() {
        if (m_head.hasNext()) {
            return m_head.next();
        }
        else if (m_hasLast) {
            m_hasLast = false;
            return m_last;
        }
        else {
            throw new NoSuchElementException();
        }

    }
    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }


}
