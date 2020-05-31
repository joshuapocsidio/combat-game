package model.player;
/**
 * Observer Interface - HealObserver
 * - method showHealEvent() for displaying event of life points healed by any given player
 * */
public interface HealObserver
{
    void showHealEvent(CombatPlayer player, int healed);
}
