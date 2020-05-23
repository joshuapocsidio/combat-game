package com.joshuapocsidio.model.player.enemy;

/**
 * Model class for GoblinEnemy inheriting from EnemyPlayer abstract class
 *
 * NOTE - the fields are set as static for ease of readability. Rather than having all
 * values as they are, they are set as fields to easily monitor which values belong to
 * what type of field.
 */
public class GoblinEnemy extends EnemyPlayer
{
    private static final String SPECIE_NAME = "Goblin";
    private static final int MAX_HEALTH = 30;
    private static final int GOLD = 20;
    private static final int MIN_DAMAGE = 3;
    private static final int MAX_DAMAGE = 8;
    private static final int MIN_DEFENSE = 4;
    private static final int MAX_DEFENSE = 8;
    private static final int SPECIAL_PROBABILITY = 50;

    public GoblinEnemy()
    {
        super(SPECIE_NAME, MAX_HEALTH, GOLD, MIN_DAMAGE, MAX_DAMAGE, MIN_DEFENSE, MAX_DEFENSE, SPECIAL_PROBABILITY);
    }

    /**
     * Template method hook for Goblin specific special abilities
     * - adds 3 flat damage on top of goblin's attack damage
     * - notifies SpecialAbilityListeners
     *
     * RETURN
     * - damage     : int
     * */
    protected int doSpecialAbility(int currentDamage)
    {
        // Notifies listeners of special ability event
        this.notifySpecialAbilityListeners("Damage +3!");

        // Adds 3 to damage
        return currentDamage + 3;
    }

}
