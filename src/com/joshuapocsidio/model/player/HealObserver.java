package com.joshuapocsidio.model.player;

import com.joshuapocsidio.model.player.GamePlayer;

public interface HealObserver
{
    void showHealEvent(GamePlayer player, int healed);
}
