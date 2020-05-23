package com.joshuapocsidio.model.item.weapon;

public class Weapon extends WeaponItem
{
    public Weapon(String name, int minEffect, int maxEffect, int cost, String damageType, String weaponType)
    {
        super(name, minEffect, maxEffect, cost, damageType, weaponType);
    }
}
