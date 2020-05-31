package model.player;
/**
 * Observer Interface - BattleObserver
 * - method showBattleEnd() for displaying event when a full battle is over
 */
public interface BattleEndObserver
{
    void showBattleEnd(CombatPlayer player);
}
