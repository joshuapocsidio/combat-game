package com.joshuapocsidio.model.enchantment;
import java.util.*;

/**
 * Model class for enchantment database
 * - holds all types of enchantments and corresponding cost in the game
 *
 * FIELDS
 * - enchantmentNames       : List of String
 * - enchantmentNameCostMap : Map of Key = name, Value = cost
 */
public class EnchantmentDatabase
{
    /** Character Player specific Fields **/
    private final List<String> enchantmentNames;
    private final Map<String, Integer> enchantmentNameCostMap;
    private int size;

    /**
     * Default Constructor
     * - initialises data structures and size
     */
    public EnchantmentDatabase()
    {
        this.enchantmentNames = new LinkedList<>();
        this.enchantmentNameCostMap = new HashMap<>();

        this.size = enchantmentNames.size();
    }

    /**
     * Method for populating the database with default enchantments
     * - Damage +2
     * - Damage +5
     * - Fire Damage
     * - PowerUp
     */
    public void populateDefault()
    {
        enchantmentNameCostMap.put("Damage +2", 5);
        enchantmentNameCostMap.put("Damage +5", 10);
        enchantmentNameCostMap.put("Fire Damage", 20);
        enchantmentNameCostMap.put("Power-Up", 10);

        enchantmentNames.add("Damage +2");
        enchantmentNames.add("Damage +5");
        enchantmentNames.add("Fire Damage");
        enchantmentNames.add("Power-Up");

        size = enchantmentNames.size();
    }

    /**
     * Method for adding an enchantment name and cost to the database
     * - performs validity checks to ensure database is maintained appropriately
     * - does not allow to add duplicates
     */
    public void add(String enchantmentName, int cost) throws InvalidEnchantmentException
    {
        // Checks if enchantment name is valid
        if(enchantmentName.isEmpty() || enchantmentName == null )
        {
            throw new InvalidEnchantmentException("Enchantment name cannot be empty nor blank");
        }

        // Checks if enchantment cost is valid
        if(cost < 0)
        {
            throw new InvalidEnchantmentException("Enchantment cost cannot be negative");
        }

        // Checks if database already contains the enchantment data
        if(enchantmentNameCostMap.containsKey(enchantmentName))
        {
            throw new InvalidEnchantmentException("Enchantment database already contains this data");
        }

        // Updates database
        this.enchantmentNames.add(enchantmentName);
        this.enchantmentNameCostMap.put(enchantmentName, cost);
        size++;
    }

    /**
     * Method for removing an enchantment name and cost from the database
     * - performs validity checks to ensure database is maintained appropriately
     * - throws exception if other classes attempts to remove an item that doesn't exist
     */
    public void remove(String enchantmentName)
    {
        // Checks if database already contains the enchantment data
        if(!enchantmentNameCostMap.containsKey(enchantmentName))
        {
            throw new IllegalArgumentException("Enchantment database does not contain this data");
        }

        // Updates database
        this.enchantmentNames.remove(enchantmentName);
        size--;
    }

    /**
     * Method for retrieving an enchantment name from database based on cost
     * - performs validity checks to ensure database is maintained appropriately
     * - throws exception if other classes attempts to query an enchantment name that doesn't exist in database
     */
    public int retrieveCost(String enchantmentName)
    {
        if(!enchantmentNameCostMap.containsKey(enchantmentName))
        {
            throw new IllegalArgumentException("Enchantment database does not contain this data");
        }

        return enchantmentNameCostMap.get(enchantmentName);
    }

    /**
     * Method for retrieving all enchantment names available in the database
     * - ensures list cannot be modified
     */
    public List<String> retrieveAll()
    {
        return Collections.unmodifiableList(this.enchantmentNames);
    }

    /**
     * Method for checking if database contains enchantment name inside database
     */
    public boolean contains(String enchantmentName)
    {
        return this.enchantmentNames.contains(enchantmentName);
    }

    /**
     * Method for obtaining size of database
     */
    public int getSize()
    {
        return this.size;
    }

}

