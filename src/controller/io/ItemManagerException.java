package controller.io;

public class ItemManagerException extends Exception
{
    public ItemManagerException(String msg)
    {
        super(msg);
    }

    public ItemManagerException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
