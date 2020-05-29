package controller.player;

public class CharacterControllerException extends Exception
{
    public CharacterControllerException(String msg)
    {
        super(msg);
    }

    public CharacterControllerException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
