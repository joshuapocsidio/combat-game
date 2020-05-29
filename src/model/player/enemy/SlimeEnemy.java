package model.player.enemy;

/**
 * Model class for SlimeEnemy inheriting from EnemyPlayer abstract class
 *
 * NOTE - the fields are set as static for ease of readability. Rather than having all
 * values as they are, they are set as fields to easily monitor which values belong to
 * what type of field.
 */
public class SlimeEnemy extends EnemyPlayer
{
    private static final String SPECIE_NAME = "Slime";
    private static final int MAX_HEALTH = 10;
    private static final int GOLD = 10;
    private static final int MIN_DAMAGE = 3;
    private static final int MAX_DAMAGE = 5;
    private static final int MIN_DEFENSE = 0;
    private static final int MAX_DEFENSE = 2;
    private static final int SPECIAL_PROBABILITY = 20;

    public SlimeEnemy()
    {
        super(SPECIE_NAME, MAX_HEALTH, GOLD, MIN_DAMAGE, MAX_DAMAGE, MIN_DEFENSE, MAX_DEFENSE, SPECIAL_PROBABILITY );
    }

    /**
     * Template method hook for Slime specific special abilities
     * - makes the slime's attack output to be zero
     * - notifies SpecialAbilityObservers
     *
     * RETURN
     * - damage     : int
     * */
    protected int doSpecialAbility(int currentDamage)
    {
        // Makes damage 0
        currentDamage = 0;

        // Notify Observers of special ability event
        this.notifySpecialAbilityObservers("Deals no damage...");

        return currentDamage;
    }

}
