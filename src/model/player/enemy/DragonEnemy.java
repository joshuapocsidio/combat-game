package model.player.enemy;

import java.util.Random;

/**
 * Model class for DragonEnemy inheriting from EnemyPlayer abstract class
 *
 * NOTE - the fields are set as static for ease of readability. Rather than having all
 * values as they are, they are set as fields to easily monitor which values belong to
 * what type of field.
 */
public class DragonEnemy extends EnemyPlayer
{
    private static final String SPECIE_NAME = "Dragon";
    private static final int MAX_HEALTH = 100;
    private static final int GOLD = 100;
    private static final int MIN_DAMAGE = 15;
    private static final int MAX_DAMAGE = 30;
    private static final int MIN_DEFENSE = 15;
    private static final int MAX_DEFENSE = 20;
    private static final int SPECIAL_PROBABILITY = 35;

    public DragonEnemy()
    {
        super(SPECIE_NAME, MAX_HEALTH, GOLD, MIN_DAMAGE, MAX_DAMAGE, MIN_DEFENSE, MAX_DEFENSE, SPECIAL_PROBABILITY);
    }

    /**
     * Template method hook for Dragon specific special abilities
     * - allows dragon to trigger 2 different special abilities
     * - (1) Doubles the damage output
     * - (2) Heals the dragon by 10
     *
     * RETURN
     * - damage     : int
     * */
    protected int doSpecialAbility(int currentDamage)
    {
        // Generates a random number for probability between 1 to 35
        Random rand = new Random();
        int num = rand.nextInt(34) + 1;

        if(num <= 25) // If generated number is 1 to 25
        {
            // Notify Observer of special ability event
            this.notifySpecialAbilityObservers("Double damage!");

            // Current Damage multiplied by 2
            currentDamage = currentDamage * 2;
        }
        else // If generated number is 26 - 35
        {
            // Notify Observer of special ability event
            this.notifySpecialAbilityObservers("Lifesteal +10 HP! ");

            // Dragon heals by 10
            this.setHealth(this.getHealth() + 10);
        }

        return currentDamage;
    }

}
