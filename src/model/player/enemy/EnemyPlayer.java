package model.player.enemy;

import model.player.CombatPlayer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Model class for EnemyPlayer inheriting from CombatPlayer abstract class
 *
 * FIELDS
 * - minDamage          : int
 * - maxDamage          : int
 * - minDefence         : int
 * - maxDefence         : int
 * - specialProbability : int
 */
public abstract class EnemyPlayer extends CombatPlayer
{
    /** List of observers **/
    private final List<SpecialAbilityObserver> specialAbilityObservers;

    /** Enemy Player specific Fields **/
    private int minDamage;
    private int maxDamage;
    private int minDefence;
    private int maxDefence;
    private int specialProbability;

    /** Template method hook for specific enemy type special abilities */
    protected abstract int doSpecialAbility(int currentDamage);

    /** Random declaration to prevent multiple initialisation */
    private final static Random rand = new Random();

    /**
     * Constructor - with all relevant fields
     */
    public EnemyPlayer(String inName, int inMaxHealth, double inGold, int inMinDamage, int inMaxDamage, int inMinDefense, int inMaxDefense, int inSpecialProbability)
    {
        super(inName, inMaxHealth, inGold);

        minDamage = inMinDamage;
        maxDamage = inMaxDamage;
        minDefence = inMinDefense;
        maxDefence = inMaxDefense;
        specialProbability = inSpecialProbability;

        specialAbilityObservers = new LinkedList<>();
    }

    /**
     * Method for calculating the attack damage.
     * - calculates damage based on random value between min and max values
     * - calculates probability and calls template method hook for specific special ability
     *
     * RETURN
     * - calculated attack damage : int
     */
    @Override
    protected int calculateAttack()
    {
        // Generate damage between min and max values
        int damage = rand.nextInt(maxDamage - minDamage + 1) + minDamage;

        // Generate random number for probability
        int num = rand.nextInt(99)+1;
        if(num <= specialProbability)
        {
            //Do ability
            damage = this.doSpecialAbility(damage);
        }

        return damage;
    }

    /**
     * Method for calculating the blocked damage
     * - calculates damage based on random value between min and max values
     *
     * RETURN
     * - calculated blocked damage : int
     */
    @Override
    protected int calculateDefence(int damage)
    {
        return rand.nextInt(maxDefence - minDefence) + 1 + minDefence;
    }

    /** ACCESSORS */
    public int getMinDamage()
    {
        return minDamage;
    }

    public int getMaxDamage()
    {
        return maxDamage;
    }

    public int getMinDefence()
    {
        return minDefence;
    }

    public int getMaxDefence()
    {
        return maxDefence;
    }

    public int getSpecialProbability()
    {
        return specialProbability;
    }

    /** MUTATORS */
    public void setMinDamage(int minDamage)
    {
        this.minDamage = minDamage;
    }

    public void setMaxDamage(int maxDamage)
    {
        this.maxDamage = maxDamage;
    }

    public void setMinDefence(int minDefence)
    {
        this.minDefence = minDefence;
    }

    public void setMaxDefence(int maxDefence)
    {
        this.maxDefence = maxDefence;
    }

    public void setSpecialProbability(int specialProbability)
    {
        this.specialProbability = specialProbability;
    }

    /**
     * To String method for displaying string representation of enemy players
     */
    @Override
    public String toString()
    {
        return super.toString() + " (REWARD)";
    }

    /**
     * Methods for adding, removing, and notifying SpecialAbilityObservers
     */
    public void addSpecialAbilityObserver(SpecialAbilityObserver specialAbilityObserver)
    {
        specialAbilityObservers.add(specialAbilityObserver);
    }

    public void removeSpecialAbilityObserver(SpecialAbilityObserver specialAbilityObserver)
    {
        specialAbilityObservers.remove(specialAbilityObserver);
        // TODO : Throw exception if battle obs does not exist
    }

    public void notifySpecialAbilityObservers(String description)
    {
        for(SpecialAbilityObserver Observer : specialAbilityObservers)
        {
            Observer.showSpecialAbilityEvent(this, description);
        }
    }


}
