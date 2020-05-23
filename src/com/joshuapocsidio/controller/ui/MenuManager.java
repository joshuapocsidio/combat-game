package com.joshuapocsidio.controller.ui;

import com.joshuapocsidio.controller.battle.BattleController;
import com.joshuapocsidio.controller.player.PlayerController;
import com.joshuapocsidio.controller.shop.ShopController;
import com.joshuapocsidio.model.enchantment.EnchantmentDatabase;
import com.joshuapocsidio.model.item.ItemDatabase;
import com.joshuapocsidio.model.player.character.CharacterPlayer;
import com.joshuapocsidio.view.menu.Menu;
import com.joshuapocsidio.view.menu.MenuItem;
import com.joshuapocsidio.view.ui.*;
import com.joshuapocsidio.view.ui.battle.BattleUI;
import com.joshuapocsidio.view.ui.change.ChangeArmourUI;
import com.joshuapocsidio.view.ui.change.ChangeNameUI;
import com.joshuapocsidio.view.ui.change.ChangeWeaponUI;
import com.joshuapocsidio.view.ui.shop.BuyUI;
import com.joshuapocsidio.view.ui.shop.EnchantUI;
import com.joshuapocsidio.view.ui.shop.SellUI;
import com.joshuapocsidio.view.ui.shop.ShopMenuUI;

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
public class MenuManager
{
    private Menu root;

    // Models
    private final CharacterPlayer player;
    private final ItemDatabase itemDatabase;
    private final EnchantmentDatabase enchantmentDatabase;

    // Controllers
    private final PlayerController playerController;
    private final ShopController shopController;
    private final BattleController battleController;

    public MenuManager(CharacterPlayer player, ItemDatabase itemDatabase, EnchantmentDatabase enchantmentDatabase,
                       PlayerController playerController, ShopController shopController, BattleController battleController)
    {
        this.player = player;
        this.itemDatabase = itemDatabase;
        this.enchantmentDatabase = enchantmentDatabase;

        this.playerController = playerController;
        this.shopController = shopController;
        this.battleController = battleController;
    }

    /**
     * Method for initialising menu tree/hierarchy.
     * Error handling are done here so that user has the freedom
     * to create the MenuManager object and initialise it
     * at another point of code.
     */
    public void initialiseMenuTree() throws InvalidMenuManagerException
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

        if(playerController == null)
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
            throw new InvalidMenuManagerException(errorMessage);
        }

        // Initialise the root
        this.root = new MainMenuUI(player);

        // Create main menu interfaces - First level
        Menu shop = new ShopMenuUI(player, itemDatabase);
        MenuItem battle = new BattleUI(player, battleController);
        MenuItem nameChange = new ChangeNameUI(player, playerController);
        MenuItem weaponChange = new ChangeWeaponUI(player, playerController);
        MenuItem armourChange = new ChangeArmourUI(player, playerController);

        // Add main menu interfaces
        this.root.add(shop);
        this.root.add(nameChange);
        this.root.add(weaponChange);
        this.root.add(armourChange);
        this.root.add(battle);

        // Create Shop interfaces - Second level (shop)
        MenuItem buy = new BuyUI(itemDatabase, shopController);
        MenuItem sell = new SellUI(player, shopController);
        MenuItem enchant = new EnchantUI(player, enchantmentDatabase, shopController);

        // Add Shop interfaces
        shop.add(buy);
        shop.add(sell);
        shop.add(enchant);
    }

    /**
     * Method for launching and outputting the visual
     * user interface
     */
    public void showUI() throws InvalidMenuManagerException
    {
        if(root == null)
        {
            throw new InvalidMenuManagerException("Root menu needs to be initialised");
        }
        root.show();
    }

}
