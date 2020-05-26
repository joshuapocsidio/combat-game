package com.joshuapocsidio.view.ui.change;

import com.joshuapocsidio.controller.player.CharacterController;
import com.joshuapocsidio.controller.player.CharacterControllerException;
import com.joshuapocsidio.model.item.armour.ArmourItem;
import com.joshuapocsidio.model.player.character.CharacterPlayer;
import com.joshuapocsidio.view.menu.MenuItem;
import com.joshuapocsidio.view.ui.UserInterface;

import java.util.List;

/**
 * View class for ChangeArmourUI and extends MenuItem
 *
 * This class is responsible for
 * - showing interface output specific for changing the armour of character player
 * - defining how the user interface will behave under this menu interface
 * - defining what is a valid input when changing armour of character player
 */
public class ChangeArmourUI extends MenuItem
{
    /** ChangeArmourUI Fields **/
    private final CharacterPlayer player;
    private final CharacterController characterController;

    /**
     * Constructor
     */
    public ChangeArmourUI(CharacterPlayer player, CharacterController characterController)
    {
        super();
        this.menuLabel = "Change Armour";

        this.player = player;
        this.characterController = characterController;
    }

    /**
     * Method to define the string output for changing the character's armour
     *
     * This method outputs the following:
     * - player information and attributes
     * - list of armours
     *
     * NOTE - UserInterface class is used to build the string output and returns the
     * built string structure based on components wished to include in the ui
     */
    @Override
    protected String getInterfaceOutput()
    {
        UserInterface ui = new UserInterface.Builder()
                .withPreHeading("PLAYER INFO")
                .withPreInfo(player.toString())
                .withHeading("WEAPONRY")
                .withSubHeading("Your armour choice to defend from evil!")
                .withBody(player.getArmourListString() + "\n0  -  Exit")
                .withPrompt("Enter integer of weapon choice : ")
                .build();

        return ui.getOutput();
    }

    /**
     * Method to define the action to be taken if a valid user input is received.
     * This return false if there is an exception caught from the action implementation code
     * and passed back to MenuItem class to handle re-prompting user.
     *
     * This action implementation is as follows:
     * - gets all armours from player's inventory
     * - attempt to equip armour chosen from inventory
     * - if an exception is thrown from the PlayerController class, output the issue
     *
     * RETURN
     * - boolean    : indicates success of action
     */
    @Override
    protected boolean doAction(String choiceStr)
    {
        // Get all armours from player
        List<ArmourItem> armours = player.getArmours();
        try
        {
            // Equip armour obtained from the list of armours
            characterController.equipArmour(armours.get(Integer.parseInt(choiceStr) - 1));
            return true;
        }
        catch(CharacterControllerException e)
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
            if(choice > player.getArmours().size() || choice < 0)
            {
                System.out.print("Input is out of bounds - ");
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
