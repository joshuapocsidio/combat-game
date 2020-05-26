package com.joshuapocsidio.model.player.character;

import com.joshuapocsidio.model.player.CombatPlayer;

public interface PotionUseObserver
{
    void showPotionUseEvent(CombatPlayer player, String name, int damage);
}
