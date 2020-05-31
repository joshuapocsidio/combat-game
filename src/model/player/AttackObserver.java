package model.player;
/**
 * Observer Interface - AttackObserver
 * - method showAttackEvent() for displaying event of a player attacking
 */
public interface AttackObserver
{
    void showAttackEvent(CombatPlayer player, int damage);
}
