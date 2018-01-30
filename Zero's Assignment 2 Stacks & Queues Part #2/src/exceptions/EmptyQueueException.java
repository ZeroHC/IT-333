package exceptions;

/*
 * Hanchen (Zero) Liu
 * 01/29/2018
 * EmptyQueueException.java
 *
 * This is a custom exception class,
 * that is built to support EmptyQueueException
 */

/**
 * This is a custom exception class,
 * that is built to support EmptyQueueException
 *
 * @author Hanchen (Zero) Liu
 * @version 1.0
 */
public class EmptyQueueException extends RuntimeException
{
    /**
     * This method initializes a EmptyQueueException
     * @param message a custom exception message
     */
    public EmptyQueueException(String message)
    {
        super(message);
    }
}