package com.joshuapocsidio.controller.io;

import com.joshuapocsidio.controller.factory.InvalidItemFactoryException;
import com.joshuapocsidio.controller.factory.ItemFactory;
import com.joshuapocsidio.model.item.GameItem;
import com.joshuapocsidio.model.item.InvalidItemDatabaseException;
import com.joshuapocsidio.model.item.ItemDatabase;
import com.joshuapocsidio.model.item.ItemDatabaseChangeObserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * I/O class responsible for parsing items from external source
 */
public class ItemDatabaseManager implements ItemDatabaseChangeObserver
{
    private final ItemFactory itemFactory;
    private final ItemDatabase itemDatabase;

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
        if(fileName == null || fileName.isEmpty())
        {
            throw new InvalidItemDataSourceException("File name parameter cannot be empty nor blank");
        }

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
                    this.processLine(line);
                }
                line = bfrReader.readLine();
            }
        }
        catch(IOException e)
        {
            throw new InvalidItemDataSourceException("Unable to read file - Please check integrity of file", e);
        }
    }

    /**
     * Method for processing each line of a text file
     */
    private void processLine(String line)
    {
        /* Split parameters */
        String[] parameters = line.split(",");

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

        // Attempt to create item
        try
        {
            GameItem newItem = itemFactory.createItem(itemType, itemName, itemMin, itemMax, itemCost, attributes);
            itemDatabase.addItem(newItem);
        }
        catch(InvalidItemDatabaseException e)
        {
            // TODO : LOG THE ITEM in ERROR LOG
        }
        catch(InvalidItemFactoryException e)
        {
            // TODO : LOG THE ITEM in ERROR LOG
        }
        catch(IllegalArgumentException e)
        {
            // TODO : LOG THE ITEM IN ERROR LOG
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
            itemDatabase.addItem(newItem);
        }
        catch (InvalidItemFactoryException e)
        {
            // TODO : LOG THE ITEM IN ERROR LOG
        }
        catch (InvalidItemDatabaseException e)
        {
            // TODO : LOG THE ITEM IN ERROR LOG
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
        this.processLine(dataEntry);
    }
}
