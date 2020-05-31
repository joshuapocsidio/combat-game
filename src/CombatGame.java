import controller.battle.BattleController;
import controller.factory.*;
import controller.io.ErrorLogger;
import controller.io.ItemDatabaseManager;
import controller.io.TextFileDataLoader;
import controller.player.CharacterController;
import controller.shop.ShopController;
import model.enchantment.EnchantmentDatabase;
import model.item.InvalidItemDatabaseException;
import model.item.ItemDatabase;
import model.item.armour.ArmourItem;
import model.item.weapon.WeaponItem;
import model.player.character.CharacterPlayer;
import view.menu.MenuDirectory;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class CombatGame
{
    public static void main(String[] args)
    {
        ErrorLogger errorLogger = ErrorLogger.getInstance();
        try
        {
            /* Initialise error logging objects with formatting and link to error.log */
            final FileHandler fileHandler = new FileHandler("error.log", true);
            final SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
            errorLogger.setFileHandler(fileHandler);

            /* Initialise factories */
            final ItemFactory itemFactory = new ItemFactory();
            final EnchantmentFactory enchantmentFactory = new EnchantmentFactory();
            final EnemyFactory enemyFactory = new EnemyFactory();

            /* Initialise data source */
            final TextFileDataLoader textFileDataLoader = new TextFileDataLoader(itemFactory);

            /* Initialise databases */
            final ItemDatabase itemDatabase = new ItemDatabase();
            final ItemDatabaseManager itemDatabaseManager = new ItemDatabaseManager(itemFactory, itemDatabase);
            itemDatabaseManager.addLoader(textFileDataLoader);
            itemDatabaseManager.constructDatabase();
            final EnchantmentDatabase enchantmentDatabase = new EnchantmentDatabase();
            enchantmentDatabase.populateDefault();

            /* Create Character with default weapon and armour */
            WeaponItem weapon = itemDatabase.getCheapestWeapon();
            ArmourItem armour = itemDatabase.getCheapestArmour();
            final CharacterPlayer player = new CharacterPlayer(weapon, armour);

            /* Create Controllers */
            final CharacterController characterController = new CharacterController(player);
            final ShopController shopController = new ShopController(player, enchantmentFactory);
            final BattleController battleController = new BattleController(enemyFactory);

            /* Initialize menu manager */
            final MenuFactory menuFactory = new MenuFactory(player, itemDatabase, enchantmentDatabase, characterController, shopController, battleController);
            menuFactory.initialiseMenuTree();

            /* Get the root directory and display */
            MenuDirectory root = menuFactory.getRoot();
            root.show();

            /* Close file handler */
            fileHandler.close();
        }
        catch (InvalidMenuFactoryException | IOException | IllegalArgumentException | InvalidItemDatabaseException e)
        {
            // Fatal exceptions - All exceptions caught will be labeled as severe. Further information will be in error.log
            Logger logger = errorLogger.createLogger(CombatGame.class.getName());
            logger.setUseParentHandlers(true);
            logger.warning("SYSTEM : Something went wrong. Check error.log\n");
            logger.setUseParentHandlers(false);
            logger.severe(e.getMessage() + "\n");
        }
        finally
        {
            System.out.println("Program Terminated - Thank You");
        }
    }
}
