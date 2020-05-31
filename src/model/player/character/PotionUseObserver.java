package model.player.character;

import model.player.CombatPlayer;

/**
 * Observer Interface - PotionUseObserver
 * - method showPotionUseEvent() for displaying event of potion use
 */
public interface PotionUseObserver
{
    void showPotionUseEvent(CombatPlayer player, String potionName);
}
