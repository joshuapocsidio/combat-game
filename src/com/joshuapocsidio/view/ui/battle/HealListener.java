package com.joshuapocsidio.view.ui.battle;

import com.joshuapocsidio.model.player.GamePlayer;

public interface HealListener
{
    void showHealEvent(GamePlayer player, int healed);
}
