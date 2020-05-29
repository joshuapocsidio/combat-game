package controller.factory;

public class InvalidMenuFactoryException extends Exception
{
    public InvalidMenuFactoryException(String msg)
    {
        super(msg);
    }

    public InvalidMenuFactoryException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
