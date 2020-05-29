package com.joshuapocsidio.controller.factory;

public class InvalidItemFactoryException extends Exception
{
    public InvalidItemFactoryException(String msg)
    {
        super(msg);
    }

    public InvalidItemFactoryException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
