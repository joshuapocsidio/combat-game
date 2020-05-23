package com.joshuapocsidio.model.enchantment;

import com.joshuapocsidio.model.item.weapon.WeaponItem;

/**
 * Enchantment class
 * - Damage +2
 */
public class DamageEnchantmentA extends WeaponEnchantment
{
    public DamageEnchantmentA(WeaponItem next)
    {
        super(next, "Damage +2", 5);
    }

    /**
     * Decorator method for weapon attack function
     * - adds +2 damage to the total damage
     *
     * RETURN
     * - total damage   : int
     */
    @Override
    public int strike()
    {
        // Adds 2 damage to decorated weapon
        return next.strike() + 2;
    }
}
