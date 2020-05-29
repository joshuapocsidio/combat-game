package controller.shop;

import controller.factory.EnchantmentFactory;
import controller.factory.InvalidEnchantmentFactoryException;
import model.item.GameItem;
import model.item.weapon.WeaponItem;
import model.player.character.CharacterPlayer;
import model.player.character.CharacterPlayerException;

/**
 * Controller class for facilitating shop-related actions
 * - character buy item from shop
 * - character sell item from inventory
 * - weapon can be enchanted
 */
public class ShopController
{
    private final EnchantmentFactory enchantmentFactory;
    private final CharacterPlayer character;

    public ShopController(CharacterPlayer character, EnchantmentFactory enchantmentFactory)
    {
        if(character == null)
        {
            throw new IllegalArgumentException("Character cannot be null");
        }

        if(enchantmentFactory == null)
        {
            throw new IllegalArgumentException("Enchantment factory cannot be null");
        }

        this.character = character;
        this.enchantmentFactory = enchantmentFactory;
    }

    /**
     * Method for facilitating buy action from character
     * - checks if item is valid
     * - checks if Character has enough gold
     */
    public void buy(GameItem item) throws InvalidShopActionException
    {
        if (item == null)
        {
            throw new InvalidShopActionException("Item cannot be null");
        }

        int cost = item.getCost();
        double characterGold = character.getGold();

        if(characterGold < cost)
        {
            throw new InvalidShopActionException("Not enough gold");
        }

        // Update character's gold based on cost of purchase
        character.setGold(character.getGold() - cost);
        // Add to inventory newly bought item
        character.addToInventory(item);
    }

    /**
     * Method for facilitating sell action from character
     * - checks if item is valid
     * - checks if character has the item
     * - checks if the item is currently equipped
     */
    public void sell(GameItem item) throws InvalidShopActionException
    {
        if(item == null)
        {
            throw new InvalidShopActionException("Item must be null");
        }

        if(!character.hasItem(item))
        {
            throw new InvalidShopActionException("Item not found in inventory");
        }

        try
        {
            // Remove from inventory since item is sold
            character.removeFromInventory(item);
            // Sale price is only 50% of total price of item
            int profit = item.getCost() / 2;
            // Update character's gold based on sale profit
            character.setGold(character.getGold() + profit);
        }
        catch(CharacterPlayerException e)
        {
            throw new InvalidShopActionException("Sale could not be finalised. " + e.getMessage(), e);
        }
    }

    /**
     * Method for enchanting a weapon
     * - checks if weapon is valid
     * - checks if enchantment name is valid
     * - checks if enchantment cost is valid
     * - checks if character has enough gold to afford enchantment cost
     */
    public void enchant(WeaponItem weapon, String enchantmentName, int enchantmentCost) throws InvalidShopActionException
    {
        if(weapon == null)
        {
            throw new InvalidShopActionException("Weapon cannot be null");
        }

        if(enchantmentName.isEmpty() || enchantmentName == null )
        {
            throw new InvalidShopActionException("Enchantment name cannot be empty nor blank");
        }

        if(enchantmentCost < 0)
        {
            throw new InvalidShopActionException("Enchantment cost cannot be negative");
        }

        // Check if character has enough gold
        double characterGold = character.getGold();
        if(characterGold < enchantmentCost)
        {
            throw new InvalidShopActionException("Not enough gold for enchantment - ");
        }

        try
        {
            // Create weapon from factory
            WeaponItem enchantedWeapon = enchantmentFactory.enchantWeapon(weapon, enchantmentName);

            // Add to inventory before checking
            character.addToInventory(enchantedWeapon);

            // Check if weapon is currently equipped
            if(character.getEquippedWeapon().equals(weapon))
            {
                // If it is equipped, replace with new enchanted weapon
                character.equipWeapon(enchantedWeapon);
            }

            // Remove the un-enchanted weapon from inventory
            character.removeFromInventory(weapon);
            // Update character's gold based on cost of enchantment
            character.setGold(character.getGold() - enchantmentCost);
        }
        catch (InvalidEnchantmentFactoryException e)
        {
            throw new InvalidShopActionException("Enchantment could not be created - " + e.getMessage(), e);
        }
        catch (CharacterPlayerException e)
        {
            throw new InvalidShopActionException("Enchantment transaction could not be finalised - " + e.getMessage(), e);
        }
    }
}