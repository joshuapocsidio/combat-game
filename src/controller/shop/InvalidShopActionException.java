package controller.shop;

public class InvalidShopActionException extends Exception
{
    public InvalidShopActionException(String msg)
    {
        super(msg);
    }

    public InvalidShopActionException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
