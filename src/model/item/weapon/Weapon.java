package model.item.weapon;

import model.item.GameItem;

public class Weapon extends WeaponItem
{
    public Weapon(String name, int minEffect, int maxEffect, int cost, String damageType, String weaponType)
    {
        super(name, minEffect, maxEffect, cost, damageType, weaponType);
    }

    public Weapon(Weapon weapon)
    {
        super(weapon.getName(), weapon.getMinEffect(), weapon.getMaxEffect(), weapon.getCost(), weapon.getDamageType(), weapon.getWeaponType());
    }

    /**
     * Method for attacking function
     * - currently, there is only one type of weapon. Therefore, this subclass uses the default implementation of strike
     *
     * RETURN
     * - damage blocked : int
     */
    @Override
    public int strike()
    {
        return this.calculateEffect();
    }

    @Override
    public GameItem clone()
    {
        return new Weapon(this);
    }
}
