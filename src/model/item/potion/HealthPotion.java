package model.item.potion;

import model.player.character.CharacterPlayer;

/**
 * Model class for health potions
 *
 * Implements template method hook to pass back to parent class for health
 * potion specific functionality.
 */
public class HealthPotion extends PotionItem
{
    public HealthPotion(String name, int minEffect, int maxEffect, int cost)
    {
        super(name, minEffect, maxEffect, cost);
    }

    /**
     * Template hook method for health potion specific functionality.
     * Increases player health by calculated base effect and returns
     * 0 damage
     *
     * RETURN
     * - damage     : int
     */
    @Override
    protected int doSpecificEffect(CharacterPlayer player, int baseEffect)
    {
        player.setHealth(player.getHealth() + baseEffect);
        return 0;
    }

    /**
     * To string method for showing information on potion items.
     * Concatenated with potion item to string method to add information specific to health potions.
     *
     * RETURN
     * - information on the health potion       : String
     */
    @Override
    public String toString()
    {
        return super.toString() +
                ", Type = Healing";
    }
}
