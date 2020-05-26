package com.joshuapocsidio.model.player.enemy;

import com.joshuapocsidio.model.player.enemy.EnemyPlayer;

public interface SpecialAbilityListener
{
    void showSpecialAbilityEvent(EnemyPlayer enemy, String description);
}
