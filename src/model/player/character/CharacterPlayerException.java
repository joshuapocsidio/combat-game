package model.player.character;

public class CharacterPlayerException extends Exception
{
    public CharacterPlayerException(String msg)
    {
        super(msg);
    }

    public CharacterPlayerException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
