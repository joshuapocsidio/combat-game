package com.joshuapocsidio.model.player;

public interface DefendObserver
{
    void showDefendEvent(CombatPlayer player, int blocked);
}
