package model.player;
/**
 * Observer Interface - DamageDealtObserver
 * - method showDamageEvent() for displaying event of damage taken from any given player
 */
public interface DamageDealtObserver
{
    void showDamageEvent(CombatPlayer player, int damage);
}
