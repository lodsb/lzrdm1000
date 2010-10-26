/*
 * Project: JJack - Java bridge API for the JACK Audio Connection Kit
 * Class:   com.petersalomonsen.jjack.javasound.BlockingByteFIFO
 * Version: 0.3
 *
 * Date:    2007-12-30
 *
 * Licensed under the GNU Lesser General Public License (LGPL).
 * This comes with NO WARRANTY. See file LICENSE for details.
 *
 * Author:  Peter Johan Salomonsen
 */

package com.petersalomonsen.jjack.javasound;

/**
 * A FIFO byte buffer that blocks on read or write if trying to read or write
 * more than is available for reading/writing.
 *  
 * @author  Peter Johan Salomonsen
 * @version  0.3
 */
public class BlockingByteFIFO {

    // ------------------------------------------------------------------------
    // --- fields                                                           ---
    // ------------------------------------------------------------------------

    byte[] buffer;

    long bufferPosWrite = 0;

    long bufferPosRead = 0;

    boolean blocking = false;


    // ------------------------------------------------------------------------
    // --- constructor                                                      ---
    // ------------------------------------------------------------------------

    public BlockingByteFIFO(int size) {
        buffer = new byte[size];
    }


    // ------------------------------------------------------------------------
    // --- methods                                                          ---
    // ------------------------------------------------------------------------

    public int availableRead() {
        return (int)(bufferPosWrite - bufferPosRead);
    }

    public int availableWrite() {
        return buffer.length - availableRead();
    }

    /**
     * Read into provided byteArray - will block until all data is read
     */
    public int read(byte[] b, int off, int len) {
        int readCount = 0;

        while(readCount < len)
        {
        	// Read as much as possible
        	readCount += readLenOrAvailable(b,off+readCount,len-readCount);

        	// If we couldn't read it all, then block until more is available
        	if(readCount<len)
        		block();
        }

        return len;
    }

    /**
     * Write data of provided bytearray. Will block until all data is written.
     */
    public int write(byte[] b, int off, int len) {
        int writeCount = 0;

        while(writeCount < len)
        {
        	// Write as much as possible
        	writeCount += writeLenOrAvailable(b,off+writeCount,len-writeCount);

        	// If we couldn't write it all, then block until more is available
        	if(writeCount<len)
        		block();
        }

        return len;
    }

    /**
     *  
     * @return  the bufferPosRead
     */
    public long getBufferPosRead() {
        return bufferPosRead;
    }

    /**
     *  
     * @return  the bufferPosWrite
     */
    public long getBufferPosWrite() {
        return bufferPosWrite;
    }

    /**
     * Return the size of the buffer
     */
    public int getBufferSize() {
        return buffer.length;
    }

    /**
     * Discard unread data
     */
    public void flush() {
        bufferPosRead = bufferPosWrite;
    }

    private synchronized void unblock() {
        notify();
        blocking = false;
    }

    private synchronized void block() {
        if(!blocking)
        {
        	blocking = true;

        	try {
        		wait();
        	} catch (InterruptedException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        }
    }

    /**
     * Read all requested amount of data, or the maximum that is available for reading
     */
    private int readLenOrAvailable(byte[] b, int off, int len) {
        int availableRead = availableRead();
        if(availableRead < len)
        	len = availableRead;

        if(len>0)
        {
        	int localBufferPos = (int)(bufferPosRead%buffer.length);
        	if(localBufferPos+len > buffer.length)
        	{
        		System.arraycopy(buffer, localBufferPos, b, off, buffer.length-localBufferPos);
        		System.arraycopy(buffer, 0, b, off + (buffer.length-localBufferPos), len - (buffer.length-localBufferPos));
        	}
        	else
        		System.arraycopy(buffer, localBufferPos, b, off ,len);

        	bufferPosRead += len;

        	// Unlock write blocks waiting to write
        	unblock();
        }
        return len;
    }

    /**
     * Write all requested amount of data, or the maximum that is available for writing
     */
    private int writeLenOrAvailable(byte[] b, int off, int len) {
        int availableWrite = availableWrite();
        if(availableWrite < len)
        	len = availableWrite;

        if(len>0)
        {
        	int localBufferPos = (int)(bufferPosWrite%buffer.length);
        	if(localBufferPos+len > buffer.length)
        	{
        		System.arraycopy(b, off, buffer, localBufferPos, buffer.length-localBufferPos);
        		System.arraycopy(b, off + (buffer.length-localBufferPos), buffer, 0, len - (buffer.length-localBufferPos));
        	}
        	else
        		System.arraycopy(b, off, buffer, localBufferPos,len);

        	bufferPosWrite+=len;
        	// Unblock read blocks waiting to read
        	unblock();
        }

        return len;
    }

} // end BlockingByteFIFO
