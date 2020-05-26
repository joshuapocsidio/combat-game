package com.joshuapocsidio.model.player.character;

import com.joshuapocsidio.model.player.GamePlayer;

public interface PotionUseObserver
{
    void showPotionUseEvent(GamePlayer player, String name, int damage);
}
