package controller.io;

import controller.factory.InvalidItemFactoryException;
import controller.factory.ItemFactory;
import model.item.GameItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Strategy class TextFileDataLoader implementing ItemDatabaseLoader interface
 * - logs if any warning detected when parsing in items from data source
 * - contains load() strategy method which returns a list of game items
 */
public class TextFileDataLoader implements ItemDatabaseLoader
{
    private static final String filename = "./item_database.txt";
    private final Logger logger = ErrorLogger.getInstance().createLogger(TextFileDataLoader.class.getName());
    private final ItemFactory itemFactory;

    public TextFileDataLoader(ItemFactory itemFactory)
    {
        this.itemFactory = itemFactory;
    }

    @Override
    public List<GameItem> load() throws InvalidItemDataSourceException
    {
        // Do not want to have the logger output warnings to user
        logger.setUseParentHandlers(false);
        try
        {
            File file = new File(filename);
            BufferedReader bfrReader = new BufferedReader(new FileReader(file));

            String line = bfrReader.readLine();

            List<GameItem> items = new LinkedList<>();
            while(line != null)
            {
                /* Ignore Blank Lines - trim() removes leading and trailing spaces */
                if(line.trim().length() > 0)
                {
                    try
                    {
                        GameItem newItem = this.processLine(line);
                        if(newItem != null)
                        {
                            items.add(newItem);
                        }
                    }
                    catch (ItemManagerException e)
                    {
                        // Item is not valid item - therefore must be a warning in error.log and continue without crashing
                        logger.warning(e.getMessage() + "\n * * Moving on without parsing this line\n");
                    }
                }
                line = bfrReader.readLine();
            }

            return items;
        }
        catch(IOException e)
        {
            // Fatal exception - let main handle
            throw new InvalidItemDataSourceException("Unable to read file - " +filename);
        }
    }

    /**
     * Method for processing each line of a text file
     */
    private GameItem processLine(String line) throws ItemManagerException
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
            return this.createItem(itemType, itemName, itemMin, itemMax, itemCost, attributes);
        }
        catch(NumberFormatException e)
        {
            // If Integer.parseInt() fails...
            throw new ItemManagerException("Integer parameters cannot be parsed : minEffect, maxEffect, and/or cost");
        }
    }

    /**
     * Method for creating the item based on import parameters
     * - If item factory exception is caught, simply log to error.log
     */
    private GameItem createItem(String type, String name, int minEffect, int maxEffect, int cost, String[] attributes)
    {
        GameItem newItem = null;
        try
        {
            newItem = itemFactory.createItem(type, name, minEffect, maxEffect, cost, attributes);
        }
        catch (InvalidItemFactoryException e)
        {
            // Item already exists in database - therefore only as info and can be ignored
            logger.info("Attempted to add item to database but received error : " + e.getMessage() + "\n * * Moving on without parsing this line\n");
        }

        return newItem;
    }
}
