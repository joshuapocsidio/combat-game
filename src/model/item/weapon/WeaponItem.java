package model.item.weapon;

import model.item.GameItem;

/**
 * Model abstract class for weapon items inheriting from game item abstract class
 *
 * FIELDS
 * - damageType     : String
 * - weaponType     : String
 */
public abstract class WeaponItem extends GameItem
{
    private String damageType;
    private String weaponType;

    public WeaponItem(String name, int minEffect, int maxEffect, int cost, String damageType, String weaponType)
    {
        super(name, minEffect, maxEffect, cost);

        this.damageType = damageType;
        this.weaponType = weaponType;
    }

    /** ACCESSORS */
    public String getDamageType()
    {
        return damageType;
    }

    public String getWeaponType()
    {
        return weaponType;
    }

    /** MUTATORS */
    public void setDamageType(String damageType)
    {
        this.damageType = damageType;
    }

    public void setWeaponType(String weaponType)
    {
         this.weaponType = weaponType;
    }

    /**
     * Method for attacking function
     * - there is only one type of armour and therefore only uses calculate effect from parent class
     *
     * RETURN
     * - damage blocked : int
     */
    public int strike()
    {
        return this.calculateEffect();
    }

    @Override
    public String toString()
    {
        return "WEAPON : " +super.toString()+
                "Damage = " + this.getMinEffect() +
                " - " + this.getMaxEffect() +
                ", Damage Type = " +damageType+
                ", Weapon Type = " +weaponType+
                ", ENCHANTMENTS : ";
    }
}

