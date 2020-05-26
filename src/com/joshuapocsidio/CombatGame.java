package com.joshuapocsidio;

import com.joshuapocsidio.controller.battle.BattleController;
import com.joshuapocsidio.controller.factory.EnchantmentFactory;
import com.joshuapocsidio.controller.factory.EnemyFactory;
import com.joshuapocsidio.controller.factory.ItemFactory;
import com.joshuapocsidio.controller.io.InvalidItemDataSourceException;
import com.joshuapocsidio.controller.io.ItemDatabaseManager;
import com.joshuapocsidio.controller.player.CharacterController;
import com.joshuapocsidio.controller.shop.ShopController;
import com.joshuapocsidio.controller.ui.InvalidMenuManagerException;
import com.joshuapocsidio.controller.ui.MenuManager;
import com.joshuapocsidio.model.enchantment.EnchantmentDatabase;
import com.joshuapocsidio.model.item.ItemDatabase;
import com.joshuapocsidio.model.player.character.CharacterPlayer;

public class CombatGame
{
    private static final String DEFAULT_ITEM_DATABASE = "./resources/item_database.txt";

    public static void main(String[] args)
    {
        /* Initialise shop database */
        ItemDatabase itemDatabase = new ItemDatabase();

        /* Initialise enchantment database */
        EnchantmentDatabase enchantmentDatabase = new EnchantmentDatabase();
        enchantmentDatabase.populateDefault();
        /* Initialise factories */
        final ItemFactory itemFactory = new ItemFactory();
        final EnchantmentFactory enchantmentFactory = new EnchantmentFactory();
        final EnemyFactory enemyFactory = new EnemyFactory();

        try
        {
            ItemDatabaseManager itemDatabaseManager = new ItemDatabaseManager(itemFactory, itemDatabase);
            itemDatabaseManager.populate(DEFAULT_ITEM_DATABASE);

            /* Create Character */
            CharacterPlayer player = new CharacterPlayer(itemDatabase.getCheapestWeapon(), itemDatabase.getCheapestArmour());

            /* Create Controllers */
            CharacterController characterController = new CharacterController(player);
            ShopController shopController = new ShopController(player, enchantmentFactory);
            BattleController battleController = new BattleController(enemyFactory);

            /* Initialize menu manager and show UI */
            MenuManager menuManager = new MenuManager(player, itemDatabase, enchantmentDatabase, characterController, shopController, battleController);
            menuManager.initialiseMenuTree();
            menuManager.showUI();
        }
        catch (InvalidItemDataSourceException e)
        {
            System.out.println("System : " + e.getMessage());
            System.out.println("Check source of database");
        }
        catch (InvalidMenuManagerException e)
        {
            e.printStackTrace();
        }
    }
}
