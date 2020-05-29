package view.ui.shop;

import model.item.ItemDatabase;
import model.player.character.CharacterPlayer;
import view.menu.MenuDirectory;
import view.ui.UserInterface;

/**
 * View class for ShopMenuDirectory and extends Menu
 *
 * This class is responsible for
 * - showing interface output specific for options and corresponding interface when inside the shop
 */
public class ShopMenuDirectory extends MenuDirectory
{
    /** ShopMenuDirectory Fields **/
    private final CharacterPlayer player;
    private final ItemDatabase itemDatabase;

    /**
     * Constructor
     */
    public ShopMenuDirectory(CharacterPlayer player, ItemDatabase itemDatabase)
    {
        super();
        this.menuLabel = "Go to Shop";

        this.player = player;
        this.itemDatabase = itemDatabase;
    }

    /**
     * Method to define the string output for the shop's menu options
     *
     * This method outputs the following:
     * - player information and attributes
     * - list of items available
     * - list of options available
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
                .withHeading("GAME SHOP")
                .withSubHeading("We have all the tools you need for everyday battles.")
                .withBody(itemDatabase.getListString())
                .withFooter(this.getMenuListString())
                .withPrompt("What would you like to do?")
                .build();

        return ui.getOutput();
    }

}
