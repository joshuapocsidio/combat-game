package com.joshuapocsidio.view.ui.battle;

import com.joshuapocsidio.model.player.GamePlayer;

public interface PotionUseListener
{
    void showPotionUseEvent(GamePlayer player, String name, int damage);
}
