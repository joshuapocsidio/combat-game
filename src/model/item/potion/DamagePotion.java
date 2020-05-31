package model.item.potion;

import model.item.GameItem;
import model.player.character.CharacterPlayer;

/**
 * Model class for damage potions
 *
 * Implements template method hook to pass back to parent class for damage
 * potion specific functionality.
 */
public class DamagePotion extends PotionItem
{
    @Override
    public GameItem clone() {
        return new DamagePotion(this);
    }

    public DamagePotion(String name, int minEffect, int maxEffect, int cost)
    {
        super(name, minEffect, maxEffect, cost);
    }

    public DamagePotion(DamagePotion potion)
    {
        super(potion.getName(), potion.getMinEffect(), potion.getMaxEffect(), potion.getCost());
    }

    /**
     * Template method hook for damage potion specific functionality.
     * Does not increase player health but simply returns the calculated base effect
     *
     * RETURN
     * - damage     : int
     */
    @Override
    protected int doSpecificEffect(CharacterPlayer player, int baseEffect)
    {
        return baseEffect;
    }

    /**
     * To string method for showing information on potion items.
     * Concatenated with potion item to string method to add information specific to damage potions.
     *
     * RETURN
     * - information on the damage potion       : String
     */
    @Override
    public String toString()
    {
        return super.toString() +
                ", Type = Damage";
    }
}
