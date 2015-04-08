package com.example.impl;

import com.example.FileIterator;

import java.io.IOException;
import java.util.Iterator;

public class WrappedIterator<T>  implements FileIterator<T> {
    private final Iterator<T> m_iterator;

    public WrappedIterator(Iterator<T> iterator) {
        m_iterator = iterator;
    }

    public boolean hasNext() {
        return m_iterator.hasNext();
    }

    public T next() {
        return m_iterator.next();
    }

    public void remove() {
        m_iterator.remove();
    }

    public void close() throws IOException {

    }
}
