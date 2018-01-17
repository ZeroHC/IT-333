package exceptions;

/*
 * Hanchen (Zero) Liu
 * 01/16/2018
 * StackUnderflowException.java
 *
 * This is a custom exception class,
 * that is built to support StackUnderflowException
 */

/**
 * This is a custom exception class,
 * that is built to support StackUnderflowException
 *
 * @author Hanchen (Zero) Liu
 * @version 1.0
 */
public class StackUnderflowException extends RuntimeException
{

    /**
     * This method initializes a StackUnderflowException
     * @param message a custom exception message
     */
    public StackUnderflowException(String message)
    {
        super(message);
    }
}