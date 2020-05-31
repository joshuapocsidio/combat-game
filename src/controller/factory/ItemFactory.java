package controller.factory;

import controller.io.ErrorLogger;
import model.item.GameItem;
import model.item.armour.ArmourItem;
import model.item.potion.DamagePotion;
import model.item.potion.HealthPotion;
import model.item.potion.PotionItem;
import model.item.weapon.Weapon;
import model.item.weapon.WeaponItem;

import java.util.logging.Logger;

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
        String errorMsg = "";

        //Check for invalid parameters
        if(name.isEmpty())
        {
            errorMsg += "\n - Name cannot be blank nor empty";
        }

        if(cost < 0)
        {
            errorMsg += "\n - Cost must not be negative";
        }

        if(minEffect < 0)
        {
            errorMsg += "\n - Minimum effect must be positive";
        }

        if(maxEffect < 0)
        {
            errorMsg += "\n - Maximum effect must be positive";
        }

        if(minEffect > maxEffect)
        {
            errorMsg += "\n - Minimum effect cannot be greater than the maximum effect";
        }

        if (type.isEmpty())
        {
            errorMsg += "\n - Item type must not be null";
        }

        if(errorMsg.equals(""))
        {
            // Create item
            GameItem item;
            switch (type.toUpperCase()) // To allow for case insensitivity
            {
                case "W":
                    if (attributes.length < 2)
                    {
                        errorMsg += "\n - Weapon damageType and/or weaponType are missing";
                    }
                    else
                    {
                        String damageType = attributes[0].trim();
                        String weaponType = attributes[1].trim();
                        item = createWeapon(name, minEffect, maxEffect, cost, damageType, weaponType);
                        return item;
                    }
                    break;
                case "A":
                    if (attributes.length < 1)
                    {
                        errorMsg += "\n - Armour materialType is missing";
                    }
                    else
                    {
                        String materialType = attributes[0].trim();
                        item = createArmour(name, minEffect, maxEffect, cost, materialType);
                        return item;
                    }
                    break;
                case "P":
                    if (attributes.length < 1)
                    {
                        errorMsg += "\n - Potion potionType is missing";
                    }
                    else
                    {
                        String potionType = attributes[0].trim();
                        item = createPotion(name, minEffect, maxEffect, cost, potionType);
                        return item;
                    }
                    break;
                default:
                    errorMsg += "\n - Item Type cannot be recognised";
            }
        }

        // Will always have an error if reaches this line
        throw new InvalidItemFactoryException("Item Name - " + name + errorMsg);
    }

    /**
     * Creates weapon object
     */
    private WeaponItem createWeapon(String name, int minEffect, int maxEffect, int cost, String damageType, String weaponType) throws InvalidItemFactoryException
    {
        if(damageType.isEmpty())
        {
            throw new InvalidItemFactoryException("Damage type must not be empty nor blank");
        }

        if(weaponType.isEmpty())
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
        if(materialType.isEmpty())
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
