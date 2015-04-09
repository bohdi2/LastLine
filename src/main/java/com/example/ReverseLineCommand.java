

package com.example;
 
import java.io.*;
import java.util.Iterator;

public class ReverseLineCommand
{
    // use to test this class.
    // Just invoke and pass in a Broker.log file. It will print out the last
    // 10 trimed lines.

    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();
 
            System.err.println("Reverse: " + args[0]);

            Lines lines = new Lines(new RandomAccessFile(args[0], "r"));
            Iterator<String> r = lines.reverseLineIterator(50);

            for (int ii=0; ii<10; ii++) {
                if (r.hasNext())
                    System.err.format("Iterator: %s%n", r.next());
            }
           
            long now = System.currentTimeMillis();
            System.err.println("Time: " + (now - start));
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
 
    }
 
 }
 
