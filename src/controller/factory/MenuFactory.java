package controller.factory;

import controller.battle.BattleController;
import controller.player.CharacterController;
import controller.shop.ShopController;
import model.enchantment.EnchantmentDatabase;
import model.item.ItemDatabase;
import model.player.character.CharacterPlayer;
import view.menu.MenuDirectory;
import view.menu.MenuAction;
import view.ui.*;
import view.ui.battle.BattleView;
import view.ui.change.ChangeArmourView;
import view.ui.change.ChangeNameView;
import view.ui.change.ChangeWeaponView;
import view.ui.shop.BuyView;
import view.ui.shop.EnchantView;
import view.ui.shop.SellView;
import view.ui.shop.ShopMenuDirectory;

/**
 * Class for managing hierarchy of menus.
 * This has been implemented to allow ease of constructing new
 * hierarchical structures by using Composite pattern.
 *
 * More on this pattern in the actual classes within the pattern...
 *
 * For example, if the same menu but different hierarchy to be implemented,
 * simply edit this file and change the order of construction
 *
 * This also allows the separation of concerns between different
 * sections and segments of the game.
 */
public class MenuFactory
{
    // Root directory
    private MenuDirectory root;

    // Models
    private final CharacterPlayer player;
    private final ItemDatabase itemDatabase;
    private final EnchantmentDatabase enchantmentDatabase;

    // Controllers
    private final CharacterController characterController;
    private final ShopController shopController;
    private final BattleController battleController;

    public MenuFactory(CharacterPlayer player, ItemDatabase itemDatabase, EnchantmentDatabase enchantmentDatabase,
                       CharacterController characterController, ShopController shopController, BattleController battleController)
    {
        this.player = player;
        this.itemDatabase = itemDatabase;
        this.enchantmentDatabase = enchantmentDatabase;

        this.characterController = characterController;
        this.shopController = shopController;
        this.battleController = battleController;
    }

    /**
     * Method for initialising menu tree/hierarchy.
     * Error handling are done here so that user has the freedom
     * to create the MenuFactory object and initialise it
     * at another point of code.
     */
    public void initialiseMenuTree() throws InvalidMenuFactoryException
    {
        String errorMessage = "";
        if(player == null)
        {
            errorMessage += "Game character player, ";
        }

        if(itemDatabase == null)
        {
            errorMessage += "Shop database, ";
        }

        if(enchantmentDatabase == null)
        {
            errorMessage += "Enchantment database, ";
        }

        if(characterController == null)
        {
            errorMessage += "Player controller, ";
        }

        if(shopController == null)
        {
            errorMessage += "Shop controller, ";
        }

        if(battleController == null)
        {
            errorMessage += "Battle controller ";
        }

        if(!errorMessage.equals(""))
        {
            errorMessage += "must not be null to initialise game menu";
            throw new InvalidMenuFactoryException(errorMessage);
        }

        // Initialise the root
        this.root = new MainMenuDirectory(player);

        // Create main menu interfaces - First level
        MenuDirectory shop = new ShopMenuDirectory(player, itemDatabase);
        MenuAction battle = new BattleView(player, battleController);
        MenuAction nameChange = new ChangeNameView(player, characterController);
        MenuAction weaponChange = new ChangeWeaponView(player, characterController);
        MenuAction armourChange = new ChangeArmourView(player, characterController);

        // Add main menu interfaces
        this.root.add(shop);
        this.root.add(nameChange);
        this.root.add(weaponChange);
        this.root.add(armourChange);
        this.root.add(battle);

        // Create Shop interfaces - Second level (shop)
        MenuAction buy = new BuyView(itemDatabase, shopController);
        MenuAction sell = new SellView(player, shopController);
        MenuAction enchant = new EnchantView(player, enchantmentDatabase, shopController);

        // Add Shop interfaces
        shop.add(buy);
        shop.add(sell);
        shop.add(enchant);
    }

    /**
     * Method for launching and outputting the visual
     * user interface
     */
    public void showView() throws InvalidMenuFactoryException
    {
        if(root == null)
        {
            throw new InvalidMenuFactoryException("Root menu needs to be initialised");
        }
        root.show();
    }

    /** ACCESSOR */
    public MenuDirectory getRoot()
    {
        return this.root;
    }

}
