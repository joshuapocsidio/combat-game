package com.joshuapocsidio.controller.factory;

import com.joshuapocsidio.model.enchantment.*;
import com.joshuapocsidio.model.item.weapon.WeaponItem;

/**
 * Factory class for decorating weapons with enchantments
 */
public class EnchantmentFactory
{

    /**
     * Enchants weapon based on enchantment name.
     * If enchantment name does not match pre-defined enchantments,
     * enchantment process is interrupted and throws exception
     */
    public WeaponItem enchantWeapon(WeaponItem oldWeapon, String enchantmentName) throws InvalidEnchantmentFactoryException
    {
        if(oldWeapon == null)
        {
            throw new InvalidEnchantmentFactoryException("Weapon cannot be null");
        }

        // Decorate weapon with enchantment
        WeaponItem newWeapon;
        switch(enchantmentName)
        {
            case "Damage +2":
                newWeapon = new DamageEnchantmentA(oldWeapon);
                break;
            case "Damage +5":
                newWeapon = new DamageEnchantmentB(oldWeapon);
                break;
            case "Fire Damage":
                newWeapon = new FireEnchantment(oldWeapon);
                break;
            case "Power-Up":
                newWeapon = new PowerUpEnchantment(oldWeapon);
                break;
            default:
                throw new InvalidEnchantmentFactoryException("Enchantment type does not exist");
        }

        return newWeapon;
    }
}

