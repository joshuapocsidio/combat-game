package model.enchantment;

import model.item.weapon.WeaponItem;

/**
 * Abstract model class for weapon enchantments.
 * - extends weapon item
 * - Getters are overridden to get the 'next' weapon's relevant attributes
 * - Setters are not overridden so that if the weapon must be modified, the enchantment class is untouched
 *
 * Getters are overridden since weapon enchantment class itself does not inherit from the wrapped
 * weapon. Therefore, getters must be trickled down all the way to the original weapon. If getters were not overridden,
 * they will return null
 *
 * FIELDS
 * - next               : WeaponItem
 * - enchantmentName    : String
 * - enchantmentCost    : int
 */
public abstract class WeaponEnchantment extends WeaponItem
{
    protected WeaponItem next;
    private final String enchantmentName;
    private final int enchantmentCost;

    public WeaponEnchantment(WeaponItem next, String enchantmentName, int enchantmentCost)
    {
        super(next.getName(), next.getMinEffect(), next.getMaxEffect(), next.getCost(), next.getDamageType(), next.getWeaponType());

        this.enchantmentName = enchantmentName;
        this.enchantmentCost = enchantmentCost;

        this.next = next;
    }

    /**
     * Method to obtaining enchantment name
     */
    public String getEnchantmentName()
    {
        return enchantmentName;
    }

    /**
     * Method to obtaining enchantment cost (without cost of the decorated weapon and other enchantments)
     */
    public int getEnchantmentCost()
    {
        return enchantmentCost;
    }

    /**
     * Overridden method to getting name of actual weapon
     */
    @Override
    public String getName()
    {
        return next.getName();
    }

    /**
     * Overridden method to getting the total cost of the weapon
     * - all wrapped weapon enchantment costs and original weapon cost will be totalled
     */
    @Override
    public int getCost()
    {
        return next.getCost() + enchantmentCost;
    }

    /**
     * Overridden method to getting the max effect of actual weapon
     */
    @Override
    public int getMaxEffect()
    {
        return next.getMaxEffect();
    }

    /**
     * Overridden method to getting the min effect of actual weapon
     */
    @Override
    public int getMinEffect()
    {
        return next.getMinEffect();
    }

    /**
     * Overridden method to obtain the damage type of actual weapon
     */
    @Override
    public String getDamageType()
    {
        return next.getDamageType();
    }

    /**
     * Overridden method to obtain the weapon type of actual weapon
     */
    @Override
    public String getWeaponType()
    {
        return next.getWeaponType();
    }

    /**
     * Decorator method to trickle down to actual weapon.
     *
     * More on this inside specific weapon enchantment classes.
     */
    @Override
    public int strike()
    {
        return next.strike();
    }

    /**
     * To string method for showing enchantment information
     */
    @Override
    public String toString() {
        return next.toString() + "(" +enchantmentName+ ") " ;
    }

    /**
     * Method to obtain the string of the enchantment without information on actual weapon
     */
    public String getEnchantmentString()
    {
        return enchantmentName + " : " + enchantmentCost + " gold";
    }

    /**
     * Method for comparing weapons - with and without enchantments
     *
     * TODO : Implement better comparison
     */
    public boolean equals(WeaponItem weapon)
    {
        return weapon.toString().equals(this.toString());
    }
}
