package model.item;

import model.item.armour.ArmourItem;
import model.item.potion.PotionItem;
import model.item.weapon.WeaponItem;

import java.util.*;

/**
 * Model class for ItemDatabase
 * - holds all items currently available in the game
 *
 * FIELDS
 * - itemList       : List of GameItem
 * - itemCount      : Number of GameItems
 */
public class ItemDatabase
{
    /** Character Player specific Fields **/
    private List<GameItem> itemList;
    private int itemCount;

    /**
     * Default Constructor
     * - initialises data structure and size
     */
    public ItemDatabase()
    {
        itemList = new LinkedList<>();

        itemCount = 0;
    }

    /**
     * Method for adding items to database
     * - performs validity checks to ensure database is maintained appropriately
     */
    public void addItem(GameItem item) throws InvalidItemDatabaseException
    {
        // Checks if item is valid and not null
        if(item == null)
        {
            throw new InvalidItemDatabaseException("Item cannot be null");
        }

        // Checks if database already contains the item data
        for(GameItem checkItem : itemList)
        {
            if(checkItem.equals(item))
            {
                throw new InvalidItemDatabaseException("Item already exists in database");
            }
        }

        // Updates database
        itemList.add(item);
        itemCount++;
    }

    /**
     * Method for removing items from database
     * - performs validity checks to ensure database is maintained appropriately
     */
    public void removeItem(GameItem item) throws InvalidItemDatabaseException
    {
        // Checks if database already contains the item data
        if(!itemList.contains(item))
        {
            throw new InvalidItemDatabaseException("Item does not exist in database");
        }

        // Updates database
        itemList.remove(item);
        itemCount--;
    }

    /** ACCESSORS */
    public int getItemCount()
    {
        return itemCount;
    }

    public GameItem getItem(int index) throws InvalidItemDatabaseException
    {
        if(index < itemList.size() && index >= 0)
        {
            return itemList.get(index);
        }
        else
        {
            throw new InvalidItemDatabaseException("Index out of bounds.", new IndexOutOfBoundsException());
        }
    }

    public List<WeaponItem> getAllWeapons()
    {
        List<WeaponItem> weapons = new LinkedList<>();

        for(GameItem item : this.getAllItems())
        {
            if(item instanceof WeaponItem)
            {
                WeaponItem weapon = (WeaponItem) item;
                weapons.add(weapon);
            }
        }
        return weapons;
    }


    public List<ArmourItem> getAllArmours()
    {
        List<ArmourItem> armours = new LinkedList<>();

        for(GameItem item : this.getAllItems())
        {
            if(item instanceof ArmourItem)
            {
                ArmourItem armour = (ArmourItem) item;
                armours.add(armour);
            }
        }
        return armours;
    }

    public List<PotionItem> getAllPotions()
    {
        List<PotionItem> potions = new LinkedList<>();

        for(GameItem item : this.getAllItems())
        {
            if(item instanceof PotionItem)
            {
                PotionItem potion = (PotionItem) item;
                potions.add(potion);
            }
        }
        return potions;
    }

    public List<GameItem> getAllItems()
    {
        return Collections.unmodifiableList(itemList);
    }

    /**
     * Method for obtaining cheapest weapon available from the database
     * - places all cost available into a list and uses Collections.min() function
     *
     * RETURN
     * - cheapestWeapon     : WeaponItem
     */
    public WeaponItem getCheapestWeapon()
    {
        WeaponItem cheapestWeapon = null;
        List<Integer> costs = new LinkedList<>();

        // Iterate through all weapons
        for(WeaponItem weapon : this.getAllWeapons())
        {
            // Add cost to list of costs
            costs.add(weapon.getCost());

            // If this cost is the minimum, make it the cheapest
            if(weapon.getCost() == Collections.min(costs))
            {
                cheapestWeapon = weapon;
            }
        }
        return cheapestWeapon;
    }

    /**
     * Method for obtaining cheapest armour available from the database
     * - places all cost available into a list and uses Collections.min() function
     *
     * RETURN
     * - cheapestArmour     : ArmourItem
     */
    public ArmourItem getCheapestArmour()
    {
        ArmourItem cheapestArmour = null;
        List<Integer> costs = new LinkedList<>();

        // Iterate through all weapons
        for(ArmourItem armour : this.getAllArmours())
        {
            // Add cost to list of costs
            costs.add(armour.getCost());

            // If this cost is the minimum, make it the cheapest
            if(armour.getCost() == Collections.min(costs))
            {
                cheapestArmour = armour;
            }
        }
        return cheapestArmour;
    }

    /**
     * Method for obtaining cheapest potion available from the database
     * - places all cost available into a list and uses Collections.min() function
     *
     * RETURN
     * - cheapestPotion     : PotionItem
     */
    public PotionItem getCheapestPotion()
    {
        PotionItem cheapestPotion = null;
        List<Integer> costs = new LinkedList<>();

        // Iterate through all weapons
        for(PotionItem potion : this.getAllPotions())
        {
            // Add cost to list of costs
            costs.add(potion.getCost());

            // If this cost is the minimum, make it the cheapest
            if(potion.getCost() == Collections.min(costs))
            {
                cheapestPotion = potion;
            }
        }
        return cheapestPotion;
    }

    @Override
    public String toString()
    {
        String out = "";

        int index = 1;
        for(GameItem item : itemList)
        {
            out += index + "  -  " + item.toString() + "\n";

            index++;
        }
        return out;
    }

    /**
     * Method for obtaining string representation of all items in database
     */
    public String getListString()
    {
        List<GameItem> itemList = this.getAllItems();

        String out = "\n";
        int index = 1;
        for(GameItem item : itemList)
        {
            String info = item.toString();

            out += index + "  -  " +info+ "\n";

            index++;
        }

        return out;
    }

}
