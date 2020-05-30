package controller.io;

import controller.factory.InvalidItemFactoryException;
import controller.factory.ItemFactory;
import model.item.GameItem;
import model.item.InvalidItemDatabaseException;
import model.item.ItemDatabase;
import model.item.ItemDatabaseChangeObserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * I/O class responsible for parsing items from external source
 */
public class ItemDatabaseManager implements ItemDatabaseChangeObserver
{
    private final ItemFactory itemFactory;
    private final ItemDatabase itemDatabase;
    private final Logger logger = ErrorLogger.getInstance().createLogger(ItemDatabaseManager.class.getName());

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

    /**
     * Method for populating the database based on a text file
     */
    public void populate(String fileName) throws InvalidItemDataSourceException
    {
        // Do not want to have the logger output warnings to user
        logger.setUseParentHandlers(false);

        if(fileName.isEmpty())
        {
            throw new InvalidItemDataSourceException("File name parameter cannot be empty nor blank");
        }

        // Inner try catch are for warnings - only log the errors and continue
        // Outer try catch is for fatal exception - program should not proceed
        try
        {
            File file = new File(fileName);
            BufferedReader bfrReader = new BufferedReader(new FileReader(file));

            String line = bfrReader.readLine();

            while(line != null)
            {
                /* Ignore Blank Lines - trim() removes leading and trailing spaces */
                if(line.trim().length() > 0)
                {
                    try
                    {
                        this.processLine(line);
                    }
                    catch (ItemManagerException e)
                    {
                        // Item is not valid item - therefore must be a warning in error.log and continue without crashing
                        logger.warning(e.getMessage() + "\n * * Moving on without parsing this line\n");
                    }
                }
                line = bfrReader.readLine();
            }
        }
        catch(IOException e)
        {
            // Fatal exception - let main handle
            throw new InvalidItemDataSourceException("Unable to read file - Please check integrity of file", e);
        }
    }

    /**
     * Method for processing each line of a text file
     */
    private void processLine(String line) throws ItemManagerException
    {
        // Attempt to create item

        /* Split parameters */
        String[] parameters = line.split(",");
        if(parameters.length < 6)
        {
            // Item already exists in database - therefore only as info and can be ignored
            throw new ItemManagerException("Item input from data cannot be parsed - not enough information");
        }

        try
        {
            /* Obtain common parameters */
            String itemType = parameters[0].trim();
            /* Remove leading whitespace from name*/
            String itemName = parameters[1].trim();
            /* Remove all whitespace from numerical parameters*/
            int itemMin = Integer.parseInt(parameters[2].replaceAll("\\s", ""));
            int itemMax = Integer.parseInt(parameters[3].replaceAll("\\s", ""));
            int itemCost = Integer.parseInt(parameters[4].replaceAll("\\s", ""));
            // Create copy of the attribute array
            String[] attributes = Arrays.copyOfRange(parameters, 5, parameters.length);

            // Create and Add new item
            this.addNewItem(itemType, itemName, itemMin, itemMax, itemCost, attributes);
        }
        catch(NumberFormatException e)
        {
            // If Integer.parseInt() fails...
            throw new ItemManagerException("Integer parameters cannot be parsed : minEffect, maxEffect, and/or cost");
        }
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

    /**
     * Observer method for creating new item based on a single line string entry for future use
     *
     * For example, if an alternate source of shop item data is to be added, simply attach this as an observer
     * from the main to the subject class
     */
    @Override
    public void addNew(String dataEntry)
    {
        try
        {
            this.processLine(dataEntry);
        }
        catch (ItemManagerException e)
        {
            // Item is not valid item - therefore must be a warning in error.log
            logger.warning(e.getMessage() + "\n**Moving on without parsing this line\n");
        }
    }
}
