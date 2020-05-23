package com.joshuapocsidio.controller.shop;

import com.joshuapocsidio.controller.factory.EnchantmentFactory;
import com.joshuapocsidio.controller.factory.InvalidEnchantmentFactoryException;
import com.joshuapocsidio.model.item.GameItem;
import com.joshuapocsidio.model.item.weapon.WeaponItem;
import com.joshuapocsidio.model.player.character.CharacterPlayer;
import com.joshuapocsidio.model.player.character.CharacterPlayerException;

/**
 * Controller class for facilitating shop-related actions
 * - character buy item from shop
 * - character sell item from inventory
 * - weapon can be enchanted
 */
public class ShopController
{
    private final EnchantmentFactory enchantmentFactory;
    private final CharacterPlayer player;

    public ShopController(CharacterPlayer player, EnchantmentFactory enchantmentFactory)
    {
        if(player == null)
        {
            throw new IllegalArgumentException("Character player cannot be null");
        }

        if(enchantmentFactory == null)
        {
            throw new IllegalArgumentException("Enchantment factory cannot be null");
        }

        this.player = player;
        this.enchantmentFactory = enchantmentFactory;
    }

    /**
     * Method for facilitating buy action from character
     * - checks if item is valid
     * - checks if player has enough gold
     */
    public void buy(GameItem item) throws InvalidShopActionException
    {
        if (item == null)
        {
            throw new InvalidShopActionException("Item cannot be null");
        }

        int cost = item.getCost();
        double playerGold = player.getGold();

        if(playerGold < cost)
        {
            throw new InvalidShopActionException("Not enough gold");
        }

        // Update player's gold based on cost of purchase
        player.setGold(player.getGold() - cost);
        // Add to inventory newly bought item
        player.addToInventory(item);
    }

    /**
     * Method for facilitating sell action from character
     * - checks if item is valid
     * - checks if player has the item
     * - checks if the item is currently equipped
     */
    public void sell(GameItem item) throws InvalidShopActionException
    {
        if(item == null)
        {
            throw new InvalidShopActionException("Item must be null");
        }

        if(!player.hasItem(item))
        {
            throw new InvalidShopActionException("Item not found in inventory");
        }

        try
        {
            // Remove from inventory since item is sold
            player.removeFromInventory(item);
            // Sale price is only 50% of total price of item
            int profit = item.getCost() / 2;
            // Update player's gold based on sale profit
            player.setGold(player.getGold() + profit);
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
     * - checks if player has enough gold to afford enchantment cost
     */
    public void enchant(WeaponItem weapon, String enchantmentName, int enchantmentCost) throws InvalidShopActionException
    {
        if(weapon == null)
        {
            throw new InvalidShopActionException("Weapon cannot be null");
        }

        if(enchantmentName.isEmpty() || enchantmentName.isBlank())
        {
            throw new InvalidShopActionException("Enchantment name cannot be empty nor blank");
        }

        if(enchantmentCost < 0)
        {
            throw new InvalidShopActionException("Enchantment cost cannot be negative");
        }

        // Check if player has enough gold
        double playerGold = player.getGold();
        if(playerGold < enchantmentCost)
        {
            throw new InvalidShopActionException("Not enough gold for enchantment - ");
        }

        try
        {
            // Create weapon from factory
            WeaponItem enchantedWeapon = enchantmentFactory.enchantWeapon(weapon, enchantmentName);

            // Add to inventory before checking
            player.addToInventory(enchantedWeapon);

            // Check if weapon is currently equipped
            if(player.getEquippedWeapon().equals(weapon))
            {
                // If it is equipped, replace with new enchanted weapon
                player.equipWeapon(enchantedWeapon);
            }

            // Remove the un-enchanted weapon from inventory
            player.removeFromInventory(weapon);
            // Update player's gold based on cost of enchantment
            player.setGold(player.getGold() - enchantmentCost);
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