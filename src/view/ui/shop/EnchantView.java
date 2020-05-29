package view.ui.shop;

import controller.shop.ShopController;
import controller.shop.InvalidShopActionException;
import model.enchantment.EnchantmentDatabase;
import model.item.weapon.WeaponItem;
import model.player.character.CharacterPlayer;
import view.menu.MenuAction;
import view.ui.UserInterface;

import java.util.List;
import java.util.Scanner;

/**
 * View class for EnchantView and extends MenuItem
 *
 * This class is responsible for
 * - showing interface output specific for enchanting a weapon from player's inventory
 * - defining how the user interface will behave under this menu interface
 * - defining what is a valid input when enchanting a weapon from player's inventory
 */
public class EnchantView extends MenuAction
{
    /** EnchantView Fields **/
    private final CharacterPlayer character;
    private final EnchantmentDatabase enchantmentDatabase;
    private final ShopController shopController;

    /**
     * Constructor
     */
    public EnchantView(CharacterPlayer character, EnchantmentDatabase enchantmentDatabase, ShopController shopController)
    {
        super();
        this.menuLabel = "Enchant Weapon(s)";

        this.character = character;
        this.enchantmentDatabase = enchantmentDatabase;

        this.shopController = shopController;
    }

    /**
     * Method to define the string output for enchanting a weapon
     *
     * NOTE - UserInterface class is used to build the string output and returns the
     * built string structure based on components wished to include in the ui
     */
    @Override
    protected String getInterfaceOutput()
    {
        UserInterface ui = new UserInterface.Builder()
                .withHeading("GAME SHOP")
                .withSubHeading("Section - Enchant")
                .withBody(character.getWeaponListString() + "\n0  -  Exit")
                .withPrompt("MERCHANT : Which one would you like to enchant?")
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
     * - show user the different types of enchantments available and prompts user which enchantment to use
     * - attempt to enchant the chosen weapon through ShopController
     * - if an exception is thrown from the ShopController class, output the issue
     *
     * RETURN
     * - boolean    : indicates success of action
     */
    @Override
    protected boolean doAction(String choiceStr)
    {
        Scanner input = new Scanner(System.in);
        try
        {
            // Get all weapons from player
            List<WeaponItem> weapons = character.getWeapons();
            WeaponItem chosenWeapon = weapons.get(Integer.parseInt(choiceStr) - 1);

            // Show all available enchantment names
            List<String> enchantmentNames = enchantmentDatabase.retrieveAll();
            int index = 1;
            for(String enchantmentName : enchantmentNames)
            {
                System.out.println(index + " - " + enchantmentName);
                index++;
            }

            System.out.print("\nYour Choice : ");

            // Retrieve enchantment name based on user input
            int choice = Integer.parseInt(input.nextLine());
            String enchantmentName = enchantmentNames.get(choice - 1);
            // Retrieve enchantment cost based on enchantment name
            int enchantmentCost = enchantmentDatabase.retrieveCost(enchantmentName);

            // Pass enchanting task to controller with the weapon, name, and cost
            shopController.enchant(chosenWeapon, enchantmentName, enchantmentCost);
            return true;
        }
        catch(InvalidShopActionException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
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
            if(choice > enchantmentDatabase.getSize() || choice < 0)
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
