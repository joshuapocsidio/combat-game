package com.joshuapocsidio.view.ui.battle;

import com.joshuapocsidio.model.player.GamePlayer;

public interface DefendListener
{
    void showDefendEvent(GamePlayer player, int blocked, int damageTaken);
}
