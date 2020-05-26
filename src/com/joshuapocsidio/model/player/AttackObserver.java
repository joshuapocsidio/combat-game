package com.joshuapocsidio.model.player;

import com.joshuapocsidio.model.player.GamePlayer;

public interface AttackObserver
{
    void showAttackEvent(GamePlayer player, int damage);
}
