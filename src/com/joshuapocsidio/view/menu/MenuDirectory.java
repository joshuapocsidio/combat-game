package com.joshuapocsidio.view.menu;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Abstract class for MenuDirectory and implements MenuInterface
 * - contains zero to many MenuInterface
 * - acts as the Composite Class for the Composite Design Pattern
 *
 * NOTE - To allow for consistency, 0 is reserved for the exit option.
 * This allows for the MenuDirectory option list to always start from 1, 2, etc. This must always
 * be kept in mind when defining own MenuDirectory subclasses
 */
public abstract class MenuDirectory implements MenuInterface
{
    /** Input scanner as a variable to avoid constant redeclaration */
    private final Scanner input = new Scanner(System.in);

    /** MenuDirectory Fields **/
    protected String menuLabel;
    private final List<MenuInterface> items = new LinkedList<>();
    private boolean done;

    /** Template Method Hook to get specific output of specific MenuDirectory subclass */
    protected abstract String getInterfaceOutput();

    /**
     * Method to show the user interface of MenuDirectory objects
     * - specific output is obtained from subclasses
     * - loops until exit option is chosen
     * - if user input is invalid, loop and re-prompt user
     *
     * NOTE - MenuDirectory's are implemented so that the option choices as index starts at 1.
     */
    @Override
    public void show()
    {
        // Loop until boolean variable done is set
        done = false;
        while(!done)
        {
            // Output specific interface output from subclass
            System.out.print(getInterfaceOutput());

            // Loop until a valid input is provided
            boolean isValidInput = false;
            while(!isValidInput)
            {
                // Get input from user
                int choice = this.getInput();

                // Only check further if user input is an integer <= 0
                if (choice >= 0)
                {
                    if (choice == 0) // If user input is 0
                    {
                        // Terminate this MenuDirectory
                        isValidInput = true;
                        this.terminate();
                    }
                    else if (choice > items.size()) // If user input is greater than the size of the MenuDirectory list
                    {
                        System.out.println("Input out of bounds - Please try again");
                    }
                    else // If user input is within the bounds of MenuDirectory list
                    {
                        isValidInput = true;

                        // Call the specified menu interface
                        items.get(choice - 1).show();
                    }
                }
                else
                {
                    System.out.println("Integer is required - Please try again");
                }
            }
        }

    }

    /**
     * Method to terminate the menu tree under this MenuDirectory
     *
     * NOTE - This allows implementations that can terminate all underlying menu interfaces.
     * Particularly, this is used for ending the game if the player loses. More on this
     * in the MainMenuUI class
     */
    @Override
    public void terminate()
    {
        // Iterates through the menu interfaces and terminate all underlying menu interfaces
        for(MenuInterface item : items)
        {
            item.terminate();
        }

        // Terminate this MenuDirectory interface
        this.done = true;
    }

    /**
     * Method to add a menu interface under this MenuDirectory
     */
    public void add(MenuInterface item)
    {
        if(item == null)
        {
            throw new IllegalArgumentException("Menu interface must not be null");
        }

        items.add(item);
    }

    /**
     * Method to remove a menu interface under this MenuDirectory
     */
    public void remove(MenuInterface item)
    {
        if(!items.contains(item))
        {
            throw new NoSuchElementException("Item does not exist under this menu tree");
        }

        items.remove(item);
    }

    /**
     * Method to obtain user input and validates it.
     *
     * NOTE - Since MenuDirectory class is simply a Menu Interface which allows user to navigate through the tree,
     * user input are expected to always be numerical. Therefore, this class is implemented so that it only
     * accepts user input that are integers.
     */
    private int getInput()
    {
        try
        {
            return Integer.parseInt(input.nextLine());
        }
        catch(NumberFormatException e)
        {
            return -1;
        }
    }

    /**
     * Method to obtain the menu label for this class which will be outputted as a menu option by the composite class
     * holding this menu interface.
     */
    public String getMenuLabel()
    {
        return this.menuLabel;
    }

    /**
     * Method to obtain a string representation of the MenuDirectory option list
     *
     * NOTE - For simplicity, exit option is by default a 0
     */
    protected String getMenuList()
    {
        String list = "";

        int index = 1;

        for(MenuInterface item : items)
        {
            list += index + " - " + item.getMenuLabel() + "\n";
            index++;
        }

        list = list + "\n0 - Exit";
        return list;
    }

}
