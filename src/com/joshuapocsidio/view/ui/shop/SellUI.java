package com.joshuapocsidio.view.ui.shop;

import com.joshuapocsidio.controller.shop.ShopController;
import com.joshuapocsidio.controller.shop.InvalidShopActionException;
import com.joshuapocsidio.model.item.GameItem;
import com.joshuapocsidio.model.player.character.CharacterPlayer;
import com.joshuapocsidio.model.player.character.CharacterPlayerException;
import com.joshuapocsidio.view.menu.MenuAction;
import com.joshuapocsidio.view.ui.UserInterface;

/**
 * View class for SellUI and extends MenuItem
 *
 * This class is responsible for
 * - showing interface output specific for when selling items to the shop
 * - defining how the user interface will behave under this menu interface
 * - defining what is a valid input when selling items to the shop
 */
public class SellUI extends MenuAction
{
    /** SellUI Fields **/
    private final CharacterPlayer player;
    private final ShopController shopController;

    /**
     * Constructor
     */
    public SellUI(CharacterPlayer player, ShopController shopController)
    {
        super();
        this.menuLabel = "Sell Item(s)";

        this.player = player;
        this.shopController = shopController;
    }

    /**
     * Method to define the string output for when buying items from the shop
     *
     * This method outputs the following:\
     * - list of items from the player inventory
     *
     * NOTE - UserInterface class is used to build the string output and returns the
     * built string structure based on components wished to include in the ui
     */
    @Override
    protected String getInterfaceOutput()
    {
        UserInterface ui = new UserInterface.Builder()
                .withHeading("GAME SHOP")
                .withSubHeading("Section - Sell")
                .withBody(player.getItemListString() + "\n0  -  Exit")
                .withPrompt("MERCHANT : Which one would you like to sell?")
                .build();

        return ui.getOutput();
    }

    /**
     * Method to define the action to be taken if a valid user input is received.
     * This return false if there is an exception caught from the action implementation code
     * and passed back to MenuItem class to handle re-prompting user.
     *
     * This action implementation is as follows:
     * - attempt to get item chosen by the user from the player's inventory
     * - attempt to sell item through the shop controller
     * - if an exception is thrown from the CharacterPlayer and ShopController, output the issue
     *
     * RETURN
     * - boolean    : indicates success of action
     */
    @Override
    protected boolean doAction(String choiceStr)
    {
        try
        {
            // Get item from the player inventory
            GameItem chosenItem = player.getItem(Integer.parseInt(choiceStr) - 1);
            // Sell item through the shop controller
            shopController.sell(chosenItem);
            return true;
        }
        catch(InvalidShopActionException e)
        {
            System.out.println(e.getMessage() + " - Please try again");
        }
        catch (CharacterPlayerException e)
        {
            System.out.println(e.getMessage() + " - Please try again");
        }
        return false;
    }

    /**
     * Method which defines what kind if user input is valid
     *
     * This method checks if user input is within the valid range of integer
     * values.
     *
     * RETURN
     * - boolean        : indicates validity of user input
     */
    @Override
    protected boolean isValid(String choiceStr)
    {
        try
        {
            // Attempt to convert string to integer
            int choice = Integer.parseInt(choiceStr);

            // Invalid if user is either negative or greater than the option list size
            if(choice > player.getInventory().size() || choice < 0)
            {
                System.out.print("Input is out of bounds! ");
                return false;
            }
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }

}

