package com.joshuapocsidio.controller.ui;

public class InvalidMenuManagerException extends Exception
{
    public InvalidMenuManagerException(String msg)
    {
        super(msg);
    }

    public InvalidMenuManagerException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
