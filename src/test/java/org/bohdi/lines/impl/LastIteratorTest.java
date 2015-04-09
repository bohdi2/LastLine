package org.bohdi.lines.impl;

import org.bohdi.lines.impl.LastIterator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LastIteratorTest {

    @Test
    public void test_Normal_Tail() {
        Iterator<String> iter = new LastIterator<String>(toIterator("a", "b"), "c");
        assertEquals(toList("a", "b", "c"), toList(iter));
    }

    @Test
    public void test_Empty_Tail() {
        Iterator<String> iter = new LastIterator<String>(toIterator(), "a");
        assertEquals(toList("a"), toList(iter));
    }

    private List<String> toList(Iterator<String> iterator) {
        List<String> result = new ArrayList<String>();

        while (iterator.hasNext())
            result.add(iterator.next());

        return result;
    }

    private List<String> toList(String ... ss) {
        return Arrays.asList(ss);
    }

    private Iterator<String> toIterator(String ... ss) {
        return toList(ss).iterator();
    }
}
