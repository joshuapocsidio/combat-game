package model.enchantment;

public class InvalidEnchantmentException extends Exception
{
    public InvalidEnchantmentException(String msg)
    {
        super(msg);
    }

    public InvalidEnchantmentException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
