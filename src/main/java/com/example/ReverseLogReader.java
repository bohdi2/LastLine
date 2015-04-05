

package com.example;
 
import java.io.*;
 
public class ReverseLogReader
{
    // use to test this class.
    // Just invoke and pass in a Broker.log file. It will print out the size
    // of the file and the last timestamp.
 
    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();
 
            System.err.println("Reverse: " + args[0]);
           
            Alt alt = new Alt(new File(args[0]), 1000);
            System.err.format("Alt: %s%n", alt.readLine());
            System.err.format("Alt: %s%n", alt.readLine());
            System.err.format("Alt: %s%n", alt.readLine());
            System.err.format("Alt: %s%n", alt.readLine());
            System.err.format("Alt: %s%n", alt.readLine());
            System.err.format("Alt: %s%n", alt.readLine());
            System.err.format("Alt: %s%n", alt.readLine());
            System.err.format("Alt: %s%n", alt.readLine());
            System.err.format("Alt: %s%n", alt.readLine());


            alt.close();
 
            long now = System.currentTimeMillis();
            System.err.println("Time: " + (now - start));
            start = now;
 
            ReverseLogReader r = new ReverseLogReader(args[0], 0);
            System.err.format("Size: %d, Time: %s%n",
                              r.getFileSize(),
                              r.getLastValidTime());
 
            now = System.currentTimeMillis();
            System.err.println("Time: " + (now - start));
            start = now;
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
 
    }
 
    private RandomAccessFile randomfile;
 
    private long position;
 
    private String lastTime;
 
    private long fileSize = 0;
   
    private int buffSize = 1024;
 
    public ReverseLogReader(String filename, int filetype) throws Exception
    { 
        // Open up a random access file
        this.randomfile = new RandomAccessFile(filename, "r");
        // Set our seek position to the end of the file
        this.position = this.randomfile.length();
        fileSize = this.position;
 
        lastTime = scanToLastValidTimeLine();
       
        this.randomfile.close();
    }
 
    private String scanToLastValidTimeLine()
    {
        // for now, just read the whole file in reverse...
        for( ;; ) {
            String line;
            try {
                line = readLastLine();
                //System.err.println("Last line: " + line.length());
                if( line == null ) {
                    break;
                }
                if( isValidTimeLine(line)) {
                    return line.substring(0, 23);
                }
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace(System.err);
            }
        }
        return null;
    }
 
    private boolean isValidTimeLine(String str)
    {
        System.err.println("isValidTimeLine()");
        if (str == null || str.length() < 23) {
            return false;
        }
       
        return true;
    }
 
    public String getLastValidTime()
    {
        return lastTime;
    }
 
    // This can be made better by not accumulating any line data as we read backwards.
    // It is enough to search backwords until we find a line break, then read forward 29 or
    // so characters. We only need to grab the starting time stamp. This code originally
    // was meant to return the entire last line. This caused memory problems.
 
    private String readLastLine() throws Exception
    {
        // If our position is less than zero already, we are at the beginning
        // with nothing to return.
        if (this.position < 0) {
            return null;
            }
       
        StringBuilder lastLine = new StringBuilder();
 
        for (;;) {
            // we've reached the beginning of the file
            if (this.position <= 0) {
                return null;
            }
           
            int numBytesToRead = buffSize;
            if( position < buffSize ) {
                this.randomfile.seek(0);
                numBytesToRead = (int)position;
                position = 0;
            } else {
                //System.err.println("Seeking: " + (position-buffSize));
                this.randomfile.seek(position-buffSize);
                position -= buffSize;
            }
           
            byte[] buffer = new byte[numBytesToRead];
           
            // Read the data at this position
            int bytesRead = randomfile.read(buffer);
           
            int startOfLine = -1;
            boolean hasCR = false;
           
            // Find crlf in the buffer (char 13, char 10)
            for( int ctr = bytesRead-1; ctr >= 0 ; ctr-- ) {
                if( buffer[ctr] == 10 ) {
                    startOfLine = ctr+1;
                    if(ctr > 0 && buffer[ctr-1] == 13 ) {
                        hasCR = true;
                        break;
                    }
                }
            }
           
            int numBytesSkipped = hasCR?2:1;
           
            if( startOfLine > -1 ) {
                // If we just read a crlf by itself, return an empty line
                if( startOfLine == bytesRead) {
                    position += (bytesRead - numBytesSkipped);
                    return "";
                }
                String part = new String(buffer, startOfLine, (bytesRead-startOfLine));
                lastLine.insert(0, part);
                // Now, adjust the position so that it starts from the point before this line
                position += (bytesRead - startOfLine);
                return lastLine.toString();
            } else {
                lastLine.insert(0,new String(buffer, 0, bytesRead));
                lastLine.delete(100, lastLine.length());
            }
        }           
    }
   
    /**
     * @return Returns the fileSize.
     */
    public long getFileSize()
    {
        return fileSize;
    }
}
 
