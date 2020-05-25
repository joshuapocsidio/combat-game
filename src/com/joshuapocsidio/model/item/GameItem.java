package com.joshuapocsidio.model.item;

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
        Random rand = new Random();

        return rand.nextInt(maxEffect - minEffect + 1) + minEffect;
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
     * Equals method for comparison
     *
     * TODO : Implement better comparison algorithm
     */
    public boolean equals(GameItem item)
    {
        return item.toString().equals(this.toString());
    }


}

