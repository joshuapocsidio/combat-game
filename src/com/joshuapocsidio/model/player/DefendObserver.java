package com.joshuapocsidio.model.player;

import com.joshuapocsidio.model.player.GamePlayer;

public interface DefendObserver
{
    void showDefendEvent(GamePlayer player, int blocked, int damageTaken);
}
