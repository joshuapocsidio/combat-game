package view.ui.change;

import controller.player.CharacterController;
import controller.player.CharacterControllerException;
import model.item.weapon.WeaponItem;
import model.player.character.CharacterPlayer;
import view.menu.MenuAction;
import view.ui.UserInterface;

import java.util.List;

/**
 * View class for ChangeWeaponView and extends MenuItem
 *
 * This class is responsible for
 * - showing interface output specific for changing the weapon of character player
 * - defining how the user interface will behave under this menu interface
 * - defining what is a valid input when changing weapon of character player
 */
public class ChangeWeaponView extends MenuAction
{
    /** ChangeWeaponView Fields **/
    private final CharacterPlayer character;
    private final CharacterController characterController;

    /**
     * Constructor
     */
    public ChangeWeaponView(CharacterPlayer character, CharacterController characterController)
    {
        super();
        this.menuLabel = "Change Weapon";

        this.character = character;
        this.characterController = characterController;
    }

    /**
     * Method to define the string output for changing the character's weapon
     *
     * This method outputs the following:
     * - player information and attributes
     * - list of weapons
     *
     * NOTE - UserInterface class is used to build the string output and returns the
     * built string structure based on components wished to include in the ui
     */
    @Override
    protected String getInterfaceOutput()
    {
        UserInterface ui = new UserInterface.Builder()
                .withPreHeading("PLAYER INFO")
                .withPreInfo(character.toString())
                .withHeading("WEAPONRY")
                .withSubHeading("Your weapon choice to save the world!")
                .withBody(character.getWeaponListString() + "\n0  -  Exit")
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
     * - gets all weapons from player's inventory
     * - attempt to equip weapon chosen from inventory
     * - if an exception is thrown from the PlayerController class, output the issue
     *
     * RETURN
     * - boolean    : indicates success of action
     */
    @Override
    protected boolean doAction(String choiceStr)
    {
        // Get all weapons from player
        List<WeaponItem> weapons = character.getWeapons();
        try
        {
            // Equip weapon obtained from the list of weapons
            characterController.equipWeapon(weapons.get(Integer.parseInt(choiceStr) - 1));
            return true;
        }
        catch (CharacterControllerException e)
        {
            System.out.println(e.getMessage() + " - Please try again");
            return false;
        }
    }

    /**
     * Method which defines what kind if user input is valid.
     *
     * This method checks if user input is within the valid range of integer
     * values.
     *
     * RETURN
     * - boolean        : indicates validity of user input
     */
    @Override
    protected boolean isValid(String choiceStr) {
        try
        {
            // Attempt to convert string to integer
            int choice = Integer.parseInt(choiceStr);

            // Invalid if user is either negative or greater than the option list size
            if(choice > character.getWeapons().size() || choice < 0)
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
