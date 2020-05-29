package model.item;

public class InvalidItemException extends Exception
{
    public InvalidItemException(String msg)
    {
        super(msg);
    }

    public InvalidItemException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
