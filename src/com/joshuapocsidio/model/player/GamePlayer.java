package com.joshuapocsidio.model.player;

import com.joshuapocsidio.controller.player.PlayerControllerException;
import com.joshuapocsidio.view.ui.battle.AttackListener;
import com.joshuapocsidio.view.ui.battle.DefendListener;
import com.joshuapocsidio.view.ui.battle.HealListener;
import com.joshuapocsidio.view.ui.battle.BattleOverListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Model abstract class for GamePlayer
 *
 * FIELDS
 * - name           : String
 * - maxHealth      : int
 * - currentHealth  : int
 * - gold           : double
 *
 * LISTENERS/OBSERVERS
 * - attackListeners        : List of AttackListener
 * - defendListeners        : List of DefendListener
 * - healListeners          : List of HealListener
 * - battleOverListeners    : List of BattleOverListener
 *
 * TEMPLATE METHOD HOOKS
 * - calculateAttack()
 * - calculateDefence(int)
 */
public abstract class GamePlayer
{
    /** GamePlayer specific Fields **/
    private String name;
    private int maxHealth;
    private int currentHealth;
    private double gold;

    /** List of observers **/
    private List<AttackListener> attackListeners;
    private List<DefendListener> defendListeners;
    private List<HealListener> healListeners;
    private List<BattleOverListener> battleOverListeners;

    /** Template method hooks for attack and defend value calculations */
    protected abstract int calculateAttack();
    protected abstract int calculateDefence(int damage);

    /**
     * CONSTRUCTOR
     * - Sets up GamePlayer with relevant fields
     * - Sets up all relevant observers/listeners
     * **/
    public GamePlayer(String inName, int inMaxHealth, double inGold)
    {
        name = inName;
        maxHealth = inMaxHealth;
        gold = inGold;

        currentHealth = maxHealth;

        attackListeners = new LinkedList<>();
        defendListeners = new LinkedList<>();
        healListeners = new LinkedList<>();
        battleOverListeners = new LinkedList<>();
    }

    /**
     * Method for attack functionality
     * - calculates attack from inheriting subclasses
     * - notifies attack listeners of attack event
     *
     * NOTE - This method uses the template method hook which will be implemented
     * by inheriting subclasses.
     * **/
    public int attack()
    {
        // Calculate attack damage
        int damage = this.calculateAttack();

        // Notify attack listeners
        notifyAttackListeners(damage);
        return damage;
    }

    /**
     * Method for defend functionality
     * - calculates defend from inheriting subclasses
     * - notifies defend listeners of defend event
     *
     * NOTE - This method uses the template method hook which will be implemented
     * by inheriting subclasses.
     *
     * NOTE - Blocked damage is implemented so that you can only block what is received.
     * That is so that when the calculated blocked is greater than the actual incoming
     * damage, listeners should only be notified how much damage was blocked, not how much
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

        // Notify listeners for defend event
        this.notifyDefendListeners(blocked, finalDamage);

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

            // Notify listeners of heal event
            notifyHealListeners(healed);
        }

        // If health is zero or negative
        if(health <= 0)
        {
            // Set current health to 0 - health cannot be negative
            this.currentHealth = 0;

            // Notify listeners that battle is over
            notifyBattleOverListeners();
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
     * Methods for adding, removing, and notifying AttackListeners
     */
    public void addAttackListener(AttackListener attackListener)
    {
        attackListeners.add(attackListener);
    }

    public void removeAttackListener(AttackListener attackListener)
    {
        attackListeners.remove(attackListener);
    }

    public void notifyAttackListeners(int damage)
    {
        for(AttackListener listener : attackListeners)
        {
            listener.showAttackEvent(this, damage);
        }
    }

    /**
     * Methods for adding, removing, and notifying DefendListeners
     */
    public void addDefendListener(DefendListener defendListener)
    {
        defendListeners.add(defendListener);
    }

    public void removeDefendListener(DefendListener defendListener)
    {
        defendListeners.remove(defendListener);
    }

    public void notifyDefendListeners(int blocked, int damageTaken)
    {
        for(DefendListener listener : defendListeners)
        {
            listener.showDefendEvent(this, blocked, damageTaken);
        }
    }

    /**
     * Methods for adding, removing, and notifying HealListeners
     */
    public void addHealListener(HealListener healListener)
    {
        healListeners.add(healListener);
    }

    public void removeHealListener(HealListener healListener)
    {
        healListeners.remove(healListener);
    }

    public void notifyHealListeners(int healed)
    {
        for(HealListener healListener : healListeners)
        {
            healListener.showHealEvent(this, healed);
        }
    }

    /**
     * Methods for adding, removing, and notifying BattleOverListeners
     */
    public void addBattleOverListener(BattleOverListener battleOverListener)
    {
        battleOverListeners.add(battleOverListener);
    }

    public void removeBattleOverListener(BattleOverListener battleOverListener)
    {
        battleOverListeners.remove(battleOverListener);
    }

    public void notifyBattleOverListeners()
    {
        for(BattleOverListener battleOverListener : battleOverListeners)
        {
            battleOverListener.endBattle(this);
        }
    }
}
