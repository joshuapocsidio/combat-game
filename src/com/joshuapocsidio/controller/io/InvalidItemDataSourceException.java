package com.joshuapocsidio.controller.io;

public class InvalidItemDataSourceException extends Exception
{
    public InvalidItemDataSourceException(String msg)
    {
        super(msg);
    }

    public InvalidItemDataSourceException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
