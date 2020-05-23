package com.joshuapocsidio.model.item.potion;

public class InvalidPotionTypeException extends Exception
{
    public InvalidPotionTypeException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public InvalidPotionTypeException(String msg)
    {
        super(msg);
    }
}
