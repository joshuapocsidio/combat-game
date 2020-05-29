package model.player.enemy;

/**
 * Model class for OgreEnemy inheriting from EnemyPlayer abstract class
 *
 * NOTE - the fields are set as static for ease of readability. Rather than having all
 * values as they are, they are set as fields to easily monitor which values belong to
 * what type of field.
 */
public class OgreEnemy extends EnemyPlayer
{
    private static final String SPECIE_NAME = "Ogre";
    private static final int MAX_HEALTH = 40;
    private static final int GOLD = 40;
    private static final int MIN_DAMAGE = 5;
    private static final int MAX_DAMAGE = 10;
    private static final int MIN_DEFENSE = 6;
    private static final int MAX_DEFENSE = 12;
    private static final int SPECIAL_PROBABILITY = 20;

    public OgreEnemy()
    {
        super(SPECIE_NAME, MAX_HEALTH, GOLD, MIN_DAMAGE, MAX_DAMAGE, MIN_DEFENSE, MAX_DEFENSE, SPECIAL_PROBABILITY);
    }

    /**
     * Template method hook for Ogre specific special abilities
     * - allows ogre to attack twice in a row
     * - does not allow the second attack to trigger another special ability
     *
     * RETURN
     * - damage     : int
     * */
    protected int doSpecialAbility(int currentDamage)
    {
        // Notify Observers of special ability event
        this.notifySpecialAbilityObservers("Double Strike!");

        // Set the special ability to zero to prevent another special ability on the second strike (eg. triple/quad strike)
        this.setSpecialProbability(0);

        // Add the total damage
        currentDamage = currentDamage + this.calculateAttack();

        // Reset the special probability back to normal
        this.setSpecialProbability(SPECIAL_PROBABILITY);

        return currentDamage;
    }

}
