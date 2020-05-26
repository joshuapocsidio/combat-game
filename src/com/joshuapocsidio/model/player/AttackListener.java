package com.joshuapocsidio.model.player;

import com.joshuapocsidio.model.player.GamePlayer;

public interface AttackListener
{
    void showAttackEvent(GamePlayer player, int damage);
}
