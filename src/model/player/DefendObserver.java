package model.player;
/**
 * Observer Interface - DefendObserver
 * - method showDefendEvent() for displaying event of any given player defending incoming attack
 */
public interface DefendObserver
{
    void showDefendEvent(CombatPlayer player, int blocked);
}
