package controller.factory;

import model.item.GameItem;
import model.item.armour.ArmourItem;
import model.item.potion.DamagePotion;
import model.item.potion.HealthPotion;
import model.item.potion.PotionItem;
import model.item.weapon.Weapon;
import model.item.weapon.WeaponItem;

/**
 * Factory class for creating items.
 * Checks for validity of input parameters and ensures
 * that no illegal/invalid item objects are created.
 */
public class ItemFactory
{
    /**
     * Creates items based on input parameters
     * - Calls different weapon creation based on input type
     */
    public GameItem createItem(String type, String name, int minEffect, int maxEffect, int cost, String[] attributes) throws InvalidItemFactoryException
    {
        //Check for invalid parameters
        if(name == null || name.isEmpty())
        {
            throw new InvalidItemFactoryException("Name cannot be blank nor empty");
        }

        if(cost < 0)
        {
            throw new InvalidItemFactoryException("Cost must not be negative");
        }

        if(minEffect < 0)
        {
            throw new InvalidItemFactoryException("Minimum effect must be positive");
        }

        if(maxEffect < 0)
        {
            throw new InvalidItemFactoryException("Maximum effect must be positive");
        }

        if(minEffect > maxEffect)
        {
            throw new InvalidItemFactoryException("Minimum effect cannot be greater than the maximum effect");
        }

        if (type.isEmpty() || type == null )
        {
            throw new InvalidItemFactoryException("Item type must not be null");
        }

        // Create item
        GameItem item;
        switch (type.toUpperCase()) // To allow for case insensitivity
        {
            case "W":
                String damageType = attributes[0].trim();
                String weaponType = attributes[1].trim();
                item = createWeapon(name, minEffect, maxEffect, cost, damageType, weaponType);
                break;
            case "A":
                String materialType = attributes[0].trim();
                item = createArmour(name, minEffect, maxEffect, cost, materialType);
                break;
            case "P":
                String potionType = attributes[0].trim();
                item = createPotion(name, minEffect, maxEffect, cost, potionType);
                break;
            default:
                throw new InvalidItemFactoryException("Item Type cannot be recognised");
        }

        return item;
    }

    /**
     * Creates weapon object
     */
    private WeaponItem createWeapon(String name, int minEffect, int maxEffect, int cost, String damageType, String weaponType) throws InvalidItemFactoryException
    {
        if(damageType.isEmpty() || damageType == null )
        {
            throw new InvalidItemFactoryException("Damage type must not be empty nor blank");
        }

        if(weaponType.isEmpty() || weaponType == null )
        {
            throw new InvalidItemFactoryException("Weapon type must not be empty nor blank");
        }

        return new Weapon(name, minEffect, maxEffect, cost, damageType, weaponType);
    }

    /**
     * Creates armour object
     */
    private ArmourItem createArmour(String name, int minEffect, int maxEffect, int cost, String materialType) throws InvalidItemFactoryException
    {
        if(materialType.isEmpty() || materialType == null )
        {
            throw new InvalidItemFactoryException("Material type must not be empty nor blank");
        }

        return new ArmourItem(name, minEffect, maxEffect, cost, materialType);
    }

    /**
     * Creates potion object
     */
    private PotionItem createPotion(String name, int minEffect, int maxEffect, int cost, String potionType) throws InvalidItemFactoryException
    {
        if(potionType == null || potionType.isEmpty())
        {
            throw new InvalidItemFactoryException("Potion type must not be empty nor blank");
        }

        // Create potion item
        PotionItem potionItem;
        switch(potionType.toUpperCase()) // Allow case insensitivity
        {
            case "H":
                potionItem = new HealthPotion(name, minEffect, maxEffect, cost);
                break;
            case "D":
                potionItem = new DamagePotion(name, minEffect, maxEffect, cost);
                break;
            default:
                throw new InvalidItemFactoryException("Potion type cannot be recognised");
        }

        return potionItem;
    }
}
