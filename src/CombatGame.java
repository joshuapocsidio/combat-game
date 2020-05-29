import controller.battle.BattleController;
import controller.factory.EnchantmentFactory;
import controller.factory.EnemyFactory;
import controller.factory.ItemFactory;
import controller.io.InvalidItemDataSourceException;
import controller.io.ItemDatabaseManager;
import controller.player.CharacterController;
import controller.shop.ShopController;
import controller.factory.InvalidMenuFactoryException;
import controller.factory.MenuFactory;
import model.enchantment.EnchantmentDatabase;
import model.item.ItemDatabase;
import model.player.character.CharacterPlayer;
import view.menu.MenuDirectory;

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
