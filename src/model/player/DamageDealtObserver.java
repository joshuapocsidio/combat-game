package model.player;

public interface DamageDealtObserver
{
    void showDamageEvent(CombatPlayer player, int damage);
}
