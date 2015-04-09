package com.example;

import com.example.impl.*;

import java.io.IOException;
import java.io.RandomAccessFile;


class Lines {
    private final RandomAccessFile m_file;


    public Lines(RandomAccessFile file) {
        m_file = file;
    }

    public FileIterator<Long> offsetIterator() throws IOException {
        if (0 != m_file.length()) {
            return new FirstIterator<Long>(0L, new OffsetIterator(m_file));
        }
        else {
            return new EmptyIterator<Long>();
        }
    }

    public FileIterator<Long> reverseOffsetIterator() throws IOException {
        if (0 != m_file.length()) {
            return new LastIterator<Long>(new ReverseOffsetIterator(m_file), 0L);
        }
        else {
            return new EmptyIterator<Long>();
        }
    }

    public FileIterator<String> lineIterator() throws IOException {
        return lineIterator(-1);
    }

    public FileIterator<String> lineIterator(int trim) throws IOException {
        return new LineIterator(m_file, offsetIterator(), trim);
    }


    public FileIterator<String> reverseLineIterator() throws IOException {
        return reverseLineIterator(-1);
    }

    public FileIterator<String> reverseLineIterator(int trim) throws IOException {
        return new LineIterator(m_file, reverseOffsetIterator(), trim);
    }

}
