package com.joshuapocsidio.controller.player;

public class PlayerControllerException extends Exception
{
    public PlayerControllerException(String msg)
    {
        super(msg);
    }

    public PlayerControllerException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
