package controller.io;

import controller.factory.InvalidItemFactoryException;
import controller.factory.ItemFactory;
import model.item.GameItem;
import model.item.InvalidItemDatabaseException;
import model.item.ItemDatabase;
import model.item.ItemDatabaseChangeObserver;
import model.item.armour.ArmourItem;
import model.item.weapon.WeaponItem;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * I/O class responsible for parsing items from external source
 */
public class ItemDatabaseManager implements ItemDatabaseChangeObserver
{
    /** ItemDatabaseManager specific Fields **/
    private final ItemFactory itemFactory;
    private final ItemDatabase itemDatabase;

    /** Logger field */
    private final Logger logger = ErrorLogger.getInstance().createLogger(ItemDatabaseManager.class.getName());

    /** List of ItemDatabaseLoaders */
    private List<ItemDatabaseLoader> loaders = new LinkedList<>();

    public ItemDatabaseManager(ItemFactory itemFactory, ItemDatabase itemDatabase)
    {
        if(itemFactory == null)
        {
            throw new IllegalArgumentException("Item factory must be initialised");
        }

        if(itemDatabase == null)
        {
            throw new IllegalArgumentException("Item database must be initialised");
        }

        this.itemFactory = itemFactory;
        this.itemDatabase = itemDatabase;
    }

    public void addLoader(ItemDatabaseLoader loader)
    {
        loaders.add(loader);
    }

    public void constructDatabase() throws InvalidItemDatabaseException {
        for(ItemDatabaseLoader loader : loaders)
        {
            try
            {
                List<GameItem> items = loader.load();

                for(GameItem item : items)
                {
                    if(!itemDatabase.contains(item))
                    {
                        itemDatabase.addItem(item);
                    }
                }
            }
            catch(InvalidItemDataSourceException e)
            {
                logger.warning(" Attempted to read data source but received error : " + e.getMessage());
            }
            catch (InvalidItemDatabaseException e)
            {
                logger.warning("Attempted to add item to database but received error : " +e.getMessage());
            }
        }

        if(itemDatabase.getAllItems().size() < 2)
        {
            throw new InvalidItemDatabaseException("Not enough items to proceed into the game");
        }

        if(!this.checkDatabaseRequirements())
        {
            throw new InvalidItemDatabaseException("Game must at least have 1 weapon and 1 armour to proceed");
        }
    }

    /**
     * Method to check for database requirements for the program to proceed
     */
    private boolean checkDatabaseRequirements()
    {
        boolean foundWeapon = false;
        boolean foundArmour = false;

        for(GameItem item : itemDatabase.getAllItems())
        {
            if(item instanceof WeaponItem)
            {
                foundWeapon = true;
            }
            else if(item instanceof ArmourItem)
            {
                foundArmour = true;
            }
        }

        return foundWeapon && foundArmour;
    }
    /**
     * Observer method for creating new item based on input parameters for future use
     *
     * For example, if an alternate source of shop item data is to be added, simply attach this as an observer
     * from the main to the subject class
     */
    @Override
    public void addNewItem(String type, String name, int minEffect, int maxEffect, int cost, String[] attributes)
    {
        GameItem newItem;
        try
        {
            newItem = itemFactory.createItem(type, name, minEffect, maxEffect, cost, attributes);
            if(newItem != null)
            {
                itemDatabase.addItem(newItem);
            }
        }
        catch (InvalidItemFactoryException e)
        {
            // Item already exists in database - therefore only as info and can be ignored
            logger.info("Attempted to add item to database but received error : " + e.getMessage() + "\n * * Moving on without parsing this line\n");
        }
        catch (InvalidItemDatabaseException e)
        {
            // Item is not valid item - therefore must be a warning in error.log
            logger.warning("Attempted to create an item in factory but received error : " + e.getMessage() + "\n * * Moving on without parsing this line\n");
        }
    }
}
