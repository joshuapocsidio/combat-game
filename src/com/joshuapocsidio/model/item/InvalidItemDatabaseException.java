package com.joshuapocsidio.model.item;

public class InvalidItemDatabaseException extends Exception
{
    public InvalidItemDatabaseException(String msg)
    {
        super(msg);
    }

    public InvalidItemDatabaseException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
