package controller.factory;

public class InvalidEnchantmentFactoryException extends Exception
{
    public InvalidEnchantmentFactoryException(String msg)
    {
        super(msg);
    }

    public InvalidEnchantmentFactoryException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
