package model.player;

import java.util.LinkedList;
import java.util.List;

/**
 * Model abstract class for CombatPlayer
 *
 * FIELDS
 * - name           : String
 * - maxHealth      : int
 * - currentHealth  : int
 * - gold           : double
 *
 * ObserverS/OBSERVERS
 * - attackObservers        : List of AttackObserver
 * - defendObservers        : List of DefendObserver
 * - healObservers          : List of HealObserver
 * - battleOverObservers    : List of BattleOverObserver
 *
 * TEMPLATE METHOD HOOKS
 * - calculateAttack()
 * - calculateDefence(int)
 */
public abstract class CombatPlayer
{
    /** CombatPlayer specific Fields **/
    private String name;
    private int maxHealth;
    private int currentHealth;
    private double gold;

    /** List of observers **/
    private List<AttackObserver> attackObservers;
    private List<DefendObserver> defendObservers;
    private List<DamageDealtObserver> damageDealtObservers;
    private List<HealObserver> healObservers;
    private List<BattleEndObserver> battleEndObservers;

    /** Template method hooks for attack and defend value calculations */
    protected abstract int calculateAttack();
    protected abstract int calculateDefence(int damage);

    /**
     * CONSTRUCTOR
     * - Sets up CombatPlayer with relevant fields
     * - Sets up all relevant observers/Observers
     * **/
    public CombatPlayer(String name, int maxHealth, double gold)
    {
        if(gold < 0)
        {
            throw new IllegalArgumentException("Gold cannot be negative");
        }

        if(maxHealth <= 0)
        {
            throw new IllegalArgumentException("Max Health cannot be 0 or negative");
        }

        this.name = name;
        this.maxHealth = maxHealth;
        this.gold = gold;

        currentHealth = this.maxHealth;

        attackObservers = new LinkedList<>();
        defendObservers = new LinkedList<>();
        damageDealtObservers = new LinkedList<>();
        healObservers = new LinkedList<>();
        battleEndObservers = new LinkedList<>();
    }

    /**
     * Method for attack functionality
     * - calculates attack from inheriting subclasses
     * - notifies attack Observers of attack event
     *
     * NOTE - This method uses the template method hook which will be implemented
     * by inheriting subclasses.
     * **/
    public int attack()
    {
        // Calculate attack damage
        int damage = this.calculateAttack();

        // Notify attack Observers
        notifyAttackObservers(damage);
        return damage;
    }

    /**
     * Method for defend functionality
     * - calculates defend from inheriting subclasses
     * - notifies defend Observers of defend event
     *
     * NOTE - This method uses the template method hook which will be implemented
     * by inheriting subclasses.
     *
     * NOTE - Blocked damage is implemented so that you can only block what is received.
     * That is so that when the calculated blocked is greater than the actual incoming
     * damage, Observers should only be notified how much damage was blocked, not how much
     * that player could have blocked in that instance.
     * **/
    public void defend(int damage)
    {
        // Calculate blocked damage
        int blocked = this.calculateDefence(damage);

        // Gets the smaller value between blocked and incoming damage
        blocked = Math.min(blocked, damage);

        // Final damage is the difference between amount blocked and incoming damage
        int finalDamage = damage - blocked;
        finalDamage = Math.max(finalDamage, 0);

        // Notify Observers for defend event
        this.notifyDefendObservers(blocked);

        // Update the health of this player
        this.setHealth(this.getHealth() - finalDamage);
    }

    /** ACCESSORS */
    public String getName()
    {
        return name;
    }
    public int getHealth()
    {
        return currentHealth;
    }

    public int getMaxHealth()
    {
        return maxHealth;
    }

    public double getGold()
    {
        return gold;
    }

    /** MUTATORS */
    public void setName(String name)
    {
        this.name = name;
    }

    public void setHealth(int health)
    {
        // If incoming health is greater than current health
        if(health > this.currentHealth)
        {
            // The difference is the amount healed
            int healed = health - this.currentHealth;

            // Notify Observers of heal event
            notifyHealObservers(healed);
        }
        else
        {
            int damage = this.currentHealth - health;

            // Notify observers of damage event
            notifyDamageDealtObservers(damage);
        }

        // If health is zero or negative
        if(health <= 0)
        {
            // Set current health to 0 - health cannot be negative
            this.currentHealth = 0;

            // Notify Observers that battle is over
            notifyBattleOverObservers();
        }
        else
        {
            // Otherwise, player's current health is the smaller amount in case health is larger than max health
            currentHealth = Math.min(maxHealth, health);
        }
    }

    public void setMaxHealth(int health)
    {
        maxHealth = health;

        if(maxHealth < currentHealth)
        {
            currentHealth = maxHealth;
        }
    }

    public void setGold(double gold)
    {
        this.gold = gold;
    }

    @Override
    public String toString() {
        return name+
                "  (" +currentHealth+
                "/" +maxHealth+ " HP)" +
                "\nGold      : " +gold;
    }

    /**
     * Methods for adding, removing, and notifying AttackObservers
     */
    public void addAttackObserver(AttackObserver attackObserver)
    {
        attackObservers.add(attackObserver);
    }

    public void removeAttackObserver(AttackObserver attackObserver)
    {
        attackObservers.remove(attackObserver);
    }

    public void notifyAttackObservers(int damage)
    {
        for(AttackObserver Observer : attackObservers)
        {
            Observer.showAttackEvent(this, damage);
        }
    }

    /**
     * Methods for adding, removing, and notifying DefendObservers
     */
    public void addDefendObserver(DefendObserver defendObserver)
    {
        defendObservers.add(defendObserver);
    }

    public void removeDefendObserver(DefendObserver defendObserver)
    {
        defendObservers.remove(defendObserver);
    }

    public void notifyDefendObservers(int blocked)
    {
        for(DefendObserver Observer : defendObservers)
        {
            Observer.showDefendEvent(this, blocked);
        }
    }

    /**
     * Methods for adding, removing, and notifying DefendObservers
     */
    public void addDamageDealtObserver(DamageDealtObserver damageDealtObserver)
    {
        damageDealtObservers.add(damageDealtObserver);
    }

    public void removeDamageDealtObserver(DamageDealtObserver damageDealtObserver)
    {
        damageDealtObservers.remove(damageDealtObserver);
    }

    public void notifyDamageDealtObservers(int damage)
    {
        for(DamageDealtObserver observer : damageDealtObservers)
        {
            observer.showDamageEvent(this, damage);
        }
    }

    /**
     * Methods for adding, removing, and notifying HealObservers
     */
    public void addHealObserver(HealObserver healObserver)
    {
        healObservers.add(healObserver);
    }

    public void removeHealObserver(HealObserver healObserver)
    {
        healObservers.remove(healObserver);
    }

    public void notifyHealObservers(int healed)
    {
        for(HealObserver healObserver : healObservers)
        {
            healObserver.showHealEvent(this, healed);
        }
    }

    /**
     * Methods for adding, removing, and notifying BattleOverObservers
     */
    public void addBattleOverObserver(BattleEndObserver battleEndObserver)
    {
        battleEndObservers.add(battleEndObserver);
    }

    public void removeBattleOverObserver(BattleEndObserver battleEndObserver)
    {
        battleEndObservers.remove(battleEndObserver);
    }

    public void notifyBattleOverObservers()
    {
        for(BattleEndObserver battleEndObserver : battleEndObservers)
        {
            battleEndObserver.showBattleEnd(this);
        }
    }
}
