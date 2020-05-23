package com.joshuapocsidio.view.ui.battle;

import com.joshuapocsidio.model.player.GamePlayer;

public interface AttackListener
{
    void showAttackEvent(GamePlayer player, int damage);
}
