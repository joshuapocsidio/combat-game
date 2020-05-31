package model.item;


import java.util.Random;

/**
 * Model class for game items
 * - Top level parent class for all item types
 *
 * Fields :
 * - name       : String
 * - cost       : int
 * - maxEffect  : int
 * - minEffect  : int
 */
public abstract class GameItem
{
    public abstract GameItem clone();
    private String name;
    private int cost;
    private int maxEffect;
    private int minEffect;

    public GameItem(String name, int minEffect, int maxEffect, int cost)
    {
        this.name = name;
        this.cost = cost;
        this.minEffect = minEffect;
        this.maxEffect = maxEffect;
    }

    public GameItem(GameItem item)
    {
        this.name = item.getName();
        this.cost = item.getCost();
        this.maxEffect = item.getMaxEffect();
        this.minEffect = item.getMinEffect();
    }

    /** ACCESSORS */
    public String getName()
    {
        return name;
    }

    public int getCost()
    {
        return cost;
    }

    public int getMaxEffect()
    {
        return maxEffect;
    }

    public int getMinEffect()
    {
        return minEffect;
    }

    /** MUTATORS */
    public void setName(String name)
    {
        this.name = name;
    }

    public void setCost(int cost) throws InvalidItemException
    {
        if(cost >= 0)
        {
            this.cost = cost;
        }
        else
        {
            throw new InvalidItemException("Cost cannot be negative");
        }
    }

    public void setMaxEffect(int maxEffect) throws InvalidItemException
    {
        if(maxEffect < 0)
        {
            throw new InvalidItemException("Max effect cannot be negative");
        }

        if(maxEffect < minEffect)
        {
            throw new InvalidItemException("Max effect cannot be less than min effect");
        }

        this.maxEffect = maxEffect;
    }

    public void setMinEffect(int minEffect) throws InvalidItemException
    {
        if(minEffect < 0)
        {
            throw new InvalidItemException("Min effect cannot be negative");
        }

        if(minEffect > maxEffect)
        {
            throw new InvalidItemException("Min effect cannot be greater than max effect");
        }

        this.minEffect = minEffect;
    }

    /**
     * Protected method for calculating the effect.
     *
     * Implemented as protected as to only allow subclasses
     * to call this function
     */
    protected int calculateEffect()
    {
        int effect;

        if(maxEffect != minEffect)
        {
            Random rand = new Random();
            effect = rand.nextInt(maxEffect - minEffect + 1) + minEffect;
        }
        else
        {
            effect = maxEffect;
        }

        return effect;
    }

    /**
     * To string method for showing game item information
     */
    @Override
    public String toString() {
        return name +
                " - COST = " +cost+
                " gold, ";
    }

    /**
     * Equals method for comparison - checks if items are identical items regardless if duplicate item
     * - Useful when looking to prevent duplication in a set
     *
     * e.g. Explosive Potion (1) ... == ExplosivePotion...
     * This will return TRUE
     */
    public boolean equals(GameItem item)
    {
        // Get name of both items
        String thisName = this.getName();
        String itemName = item.getName();

        // Obtain name without the duplicate marker "(%d)"
        String thisNameWithoutDuplicateMarker = thisName.split("[(]")[0].trim();
        String itemNameWithoutDuplicateMarker = itemName.split("[(]")[0].trim();

        // Temporarily set the names of both items to be the name without duplicate markers
        this.setName(thisNameWithoutDuplicateMarker);
        item.setName(itemNameWithoutDuplicateMarker);

        // Call isIdentical
        boolean identical = this.isIdentical(item);

        // Reset the names back to original names
        this.setName(thisName);
        item.setName(itemName);

        return identical;
    }

    /**
     * Method for checking if items are identical
     * - Useful when duplicates found
     * - Will return true if, and only if, items are of the same object
     *
     * e.g. Potion Item (1)... = Potion Item(1)....
     * This will RETURN TRUE, FALSE otherwise
     */
    public boolean isIdentical(GameItem item)
    {
        return this.toString().equals(item.toString());
    }

}

