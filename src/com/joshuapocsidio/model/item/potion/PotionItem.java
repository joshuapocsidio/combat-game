package com.joshuapocsidio.model.item.potion;

import com.joshuapocsidio.model.item.GameItem;
import com.joshuapocsidio.model.player.character.CharacterPlayer;

/**
 * Model abstract class for potion items inheriting from game item.
 *
 * Declares a protected abstract class (Template Pattern) for subclasses to implement
 */
public abstract class PotionItem extends GameItem
{
    /** Template method hook for subclasses to implement */
    protected abstract int doSpecificEffect(CharacterPlayer player, int baseEffect);

    public PotionItem(String name, int minEffect, int maxEffect,  int cost)
    {
        super(name, cost, minEffect, maxEffect);
    }

    /**
     * Method for using potions.
     * - Calls parent class' calculate effect method to create a randomized effect
     * - Calls template method hook to do specific potion things
     *
     * RETURN
     * - damage     : int
     */
    public int use(CharacterPlayer player)
    {
        int baseEffect = super.calculateEffect();

        return this.doSpecificEffect(player, baseEffect);
    }

    /**
     * To string method for showing information on potion items.
     * Concatenated with game item to string method to add information specific to potions.
     */
    @Override
    public String toString()
    {
        return "POTION : " +super.toString()+
                "Effect = " + this.getMinEffect() +
                " - " + this.getMaxEffect();
    }
}
