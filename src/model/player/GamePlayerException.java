package model.player;

public class GamePlayerException extends Exception
{
    public GamePlayerException(String msg)
    {
        super(msg);
    }

    public GamePlayerException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
