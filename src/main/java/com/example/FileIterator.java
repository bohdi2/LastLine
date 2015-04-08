package com.example;

import java.io.IOException;
import java.util.Iterator;

public interface FileIterator<E> extends Iterator<E> {
    void close() throws IOException;
}
