package com.joshuapocsidio.view.menu;

import java.util.Scanner;

/**
 * Abstract class for MenuItem and implements MenuInterface
 * - acts as the Leaf Class for the Composite Design Pattern
 *
 * NOTE - To allow for consistency, 0 is reserved for the exit option.
 * This allows for the action option list to always start from 1, 2, etc (if applicable).
 * Some implementations of this subclass may never need an integer use input and therefore
 * contains a method that allows flexibility. This exit choice can be set
 * to a different but for default consistency, 0 is set as default.
 *
 * Additionally, if this exit option is set to a non-default value, code must be implemented
 * to ensure that the users will be prompted with the appropriate exit option gui output.
 *
 * This must always be kept in mind when defining own MenuItem subclasses.
 */
public abstract class MenuItem implements MenuInterface
{
    /** Input scanner as a variable to avoid constant redeclaration */
    private final Scanner input = new Scanner(System.in);

    /** MenuItem Fields **/
    protected String menuLabel;
    private String exitOption;
    private boolean done;

    /** Template Method Hook to get specific output of specific MenuItem subclass */
    protected abstract String getInterfaceOutput();
    /** Template Method Hook to check if user input is valid */
    protected abstract boolean isValid(String choiceStr);
    /** Template Method Hook to get specific action */
    protected abstract boolean doAction(String choiceStr);

    /**
     * Default Constructor
     * - sets default exit option to a string type of 0
     */
    public MenuItem()
    {
        this.exitOption = "0";
    }

    /**
     * Method to show the user interface of MenuItem objects
     * - specific output is obtained from subclasses
     * - loops until exit option is chosen
     * - if user input is invalid, loop and re-prompt user
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
                String choiceStr = input.nextLine();

                // What is valid is defined by subclass
                isValidInput = this.isValid(choiceStr);
                if(isValidInput)
                {
                    if(choiceStr.equals(exitOption)) // If user input equals exit option
                    {
                        // Terminate this menu item
                        this.terminate();
                    }
                    else // If user input is not exit, then do action defined by subclass
                    {
                        done = doAction(choiceStr);
                    }
                }
                else
                {
                    System.out.println("Please try again");
                }

            }
        }
    }

    /**
     * Method to terminate this menu item
     */
    @Override
    public void terminate()
    {
        this.done = true;
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
     * Method to set the exit option
     */
    public void String(String exitOption)
    {
        this.exitOption = exitOption;
    }

}
