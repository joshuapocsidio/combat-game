package model.player.character;

import model.player.CombatPlayer;

public interface PotionUseObserver
{
    void showPotionUseEvent(CombatPlayer player, String name);
}
