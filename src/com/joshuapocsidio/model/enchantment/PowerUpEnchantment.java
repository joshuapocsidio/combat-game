package com.joshuapocsidio.model.enchantment;

import com.joshuapocsidio.model.item.weapon.WeaponItem;

/**
 * Enchantment class
 * - Damage x 1.1
 */
public class PowerUpEnchantment extends WeaponEnchantment
{
    public PowerUpEnchantment(WeaponItem next)
    {
        super(next, "Power-Up", 10);
    }

    /**
     * Decorator method for weapon attack function
     * - multiplies total damage by 1.1
     *
     * RETURN
     * - total damage   : int
     *
     * NOTE - If a later enchantment is added after PowerUpEnchantment, added damage resulting from those enchantments
     * will not be multiplied by 1.1
     */
    @Override
    public int strike()
    {
        // Multiplies total damage by 1.1
        return (int)(next.strike() * 1.1); // TODO : Change all damage to double to accommodate for this
    }
}
