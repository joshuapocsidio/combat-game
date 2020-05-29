package model.enchantment;

import model.item.weapon.WeaponItem;

/**
 * Enchantment class
 * - Damage +5
 */
public class DamageEnchantmentB extends WeaponEnchantment
{
    public DamageEnchantmentB(WeaponItem next)
    {
        super(next, "Damage +5", 10);
    }

    /**
     * Decorator method for weapon attack function
     * - adds +5 damage to the total damage
     *
     * RETURN
     * - total damage   : int
     */
    @Override
    public int strike()
    {
        // Adds 5 damage to decorated weapon
        return next.strike() + 5;
    }
}
