package com.joshuapocsidio.model.player;

import com.joshuapocsidio.model.player.GamePlayer;

public interface HealListener
{
    void showHealEvent(GamePlayer player, int healed);
}
