package com.example.impl;

import com.example.FileIterator;
import java.io.IOException;

public class EmptyIterator<T> implements FileIterator<T> {
    public boolean hasNext() {
        return false;
    }
    public T next() {
        return null;
    }
    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }
    public void close() throws IOException {
            }
}