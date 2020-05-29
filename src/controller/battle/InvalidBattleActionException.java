package controller.battle;

public class InvalidBattleActionException extends Exception
{
    public InvalidBattleActionException(String msg)
    {
        super(msg);
    }

    public InvalidBattleActionException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
