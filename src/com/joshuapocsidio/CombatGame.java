package com.joshuapocsidio;

import com.joshuapocsidio.controller.battle.BattleController;
import com.joshuapocsidio.controller.factory.EnchantmentFactory;
import com.joshuapocsidio.controller.factory.EnemyFactory;
import com.joshuapocsidio.controller.factory.ItemFactory;
import com.joshuapocsidio.controller.io.InvalidItemDataSourceException;
import com.joshuapocsidio.controller.io.ItemDatabaseManager;
import com.joshuapocsidio.controller.player.CharacterController;
import com.joshuapocsidio.controller.shop.ShopController;
import com.joshuapocsidio.controller.factory.InvalidMenuFactoryException;
import com.joshuapocsidio.controller.factory.MenuFactory;
import com.joshuapocsidio.model.enchantment.EnchantmentDatabase;
import com.joshuapocsidio.model.item.ItemDatabase;
import com.joshuapocsidio.model.player.character.CharacterPlayer;
import com.joshuapocsidio.view.menu.MenuDirectory;

public class CombatGame
{
    private static final String DEFAULT_ITEM_DATABASE = "./resources/item_database.txt";

    public static void main(String[] args)
    {
        try
        {
            /* Initialise factories */
            final ItemFactory itemFactory = new ItemFactory();
            final EnchantmentFactory enchantmentFactory = new EnchantmentFactory();
            final EnemyFactory enemyFactory = new EnemyFactory();

            /* Initialise databases */
            ItemDatabase itemDatabase = new ItemDatabase();
            EnchantmentDatabase enchantmentDatabase = new EnchantmentDatabase();
            enchantmentDatabase.populateDefault();
            ItemDatabaseManager itemDatabaseManager = new ItemDatabaseManager(itemFactory, itemDatabase);
            itemDatabaseManager.populate(DEFAULT_ITEM_DATABASE);

            /* Create Character with default weapon and armour */
            CharacterPlayer player = new CharacterPlayer(itemDatabase.getCheapestWeapon(), itemDatabase.getCheapestArmour());

            /* Create Controllers */
            CharacterController characterController = new CharacterController(player);
            ShopController shopController = new ShopController(player, enchantmentFactory);
            BattleController battleController = new BattleController(enemyFactory);

            /* Initialize menu manager */
            MenuFactory menuFactory = new MenuFactory(player, itemDatabase, enchantmentDatabase, characterController, shopController, battleController);
            menuFactory.initialiseMenuTree();

            /* Get the root directory and display */
            MenuDirectory root = menuFactory.getRoot();
            root.show();
        }
        catch (InvalidItemDataSourceException e)
        {
            System.out.println("System : " + e.getMessage());
            System.out.println("Check source of database");
        }
        catch (InvalidMenuFactoryException e)
        {
            e.printStackTrace();
        }
    }
}
