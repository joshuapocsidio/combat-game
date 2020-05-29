package com.joshuapocsidio.model.item.armour;

import com.joshuapocsidio.model.item.GameItem;

/**
 * Model class for armours inheriting from game item abstract class
 *
 * FIELDS
 * - materialType       : String
 */
public class ArmourItem extends GameItem
{
    private String materialType;

    public ArmourItem(String name, int minEffect, int maxEffect, int cost, String materialType)
    {
        super(name, minEffect, maxEffect, cost);

        this.materialType = materialType;
    }

    /**
     * Method for blocking function
     * - there is only one type of armour and therefore only uses calculate effect from parent class
     *
     * RETURN
     * - damage blocked : int
     */
    public int block()
    {
        return super.calculateEffect();
    }

    /** ACCESSORS */
    public String getMaterialType()
    {
        return materialType;
    }

    /** MUTATORS */
    public void setMaterialType(String materialType)
    {
        this.materialType = materialType;
    }

    /**
     * To String method for showing information of armours.
     * Concatenated with game item string to add armour specific information.
     *
     * RETURN
     * - information on the armour      : String
     */
    @Override
    public String toString() {
        return "ARMOUR : " +super.toString()+
                "Defense = " + this.getMinEffect() +
                " - " + this.getMaxEffect() +
                ", Material = " +materialType;
    }
}
