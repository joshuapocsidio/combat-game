package com.joshuapocsidio.model.player.character;

import com.joshuapocsidio.model.player.GamePlayer;

public interface PotionUseListener
{
    void showPotionUseEvent(GamePlayer player, String name, int damage);
}
