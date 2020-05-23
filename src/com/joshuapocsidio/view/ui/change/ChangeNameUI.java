package com.joshuapocsidio.view.ui.change;

import com.joshuapocsidio.controller.player.PlayerController;
import com.joshuapocsidio.controller.player.PlayerControllerException;
import com.joshuapocsidio.model.player.character.CharacterPlayer;
import com.joshuapocsidio.view.menu.MenuItem;
import com.joshuapocsidio.view.ui.UserInterface;

/**
 * View class for ChangeNameUI and extends MenuItem
 *
 * This class is responsible for
 * - showing interface output specific for changing the name of character player
 * - defining how the user interface will behave under this menu interface
 * - defining what is a valid input when changing the name of character player
 */
public class ChangeNameUI extends MenuItem
{
    /** ChangeNameUI Fields **/
    private final CharacterPlayer player;
    private final PlayerController playerController;

    /**
     * Constructor
     */
    public ChangeNameUI(CharacterPlayer player, PlayerController playerController)
    {
        super();
        this.menuLabel = "Change Character Name";

        this.player = player;
        this.playerController = playerController;
    }

    /**
     * Method to define the string output for changing the character's name
     *
     * This method outputs the following:
     * - player information and attributes
     * - player's current name
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
                .withHeading("CHANGE NAME")
                .withSubHeading("Your fighter's name will be displayed wherever you go!")
                .withBody("Current name : " + player.getName())
                .withFooter("0  -  Exit")
                .withPrompt("Enter character name : ")
                .build();

        return ui.getOutput();
    }

    /**
     * Method to define the action to be taken if a valid user input is received.
     * This return false if there is an exception caught from the action implementation code
     * and passed back to MenuItem class to handle re-prompting user.
     *
     * This action implementation is as follows:
     * - sets the name from user input string and passes it to player controller
     * - if an exception is thrown from the PlayerController class, output the issue
     *
     * RETURN
     * - boolean    : indicates success of action
     */
    @Override
    protected boolean doAction(String choiceStr)
    {
        try
        {
            playerController.setPlayerName(choiceStr);
            return true;
        }
        catch(PlayerControllerException e)
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
        // Invalid if user input string is blank, null, or empty
        return !choiceStr.isEmpty() && !choiceStr.isBlank();
    }

}
