package model.enchantment;

import model.item.weapon.WeaponItem;

import java.util.Random;

/**
 * Enchantment class
 * - Damage +5 --> +10
 */
public class FireEnchantment extends WeaponEnchantment
{
    public FireEnchantment(WeaponItem next)
    {
        super(next, "Fire Damage", 20);
    }

    /**
     * Decorator method for weapon attack function
     * - adds random damage between 5 and 10
     *
     * RETURN
     * - total damage   : int
     */
    @Override
    public int strike()
    {
        Random rand = new Random();

        // Adds damage from 5 to 10 randomly (inclusive)
        int effect = rand.nextInt(6) + 5;
        return next.strike() + effect;
    }

}
