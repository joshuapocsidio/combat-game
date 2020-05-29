package com.joshuapocsidio.model.player;

public interface DamageDealtObserver
{
    void showDamageEvent(CombatPlayer player, int damage);
}
