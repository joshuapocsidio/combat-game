package com.joshuapocsidio.controller.player;

import com.joshuapocsidio.model.item.armour.ArmourItem;
import com.joshuapocsidio.model.item.weapon.WeaponItem;
import com.joshuapocsidio.model.player.character.CharacterPlayer;
import com.joshuapocsidio.model.player.character.CharacterPlayerException;

/**
 * Controller class for using Character methods and functions
 * - set Character name
 * - equip weapon/armour
 */
public class CharacterController
{
    private final CharacterPlayer character;

    public CharacterController(CharacterPlayer character)
    {
        if(character == null)
        {
            throw new IllegalArgumentException("Character cannot be null");
        }

        this.character = character;
    }

    /**
     * Method to set the Character name and checks validity of input name
     */
    public void setCharacterName(String name) throws CharacterControllerException
    {
        if(name.isEmpty() || name.isBlank())
        {
            throw new CharacterControllerException("Character name must not be empty nor blank");
        }

        if(name.length() < 3)
        {
            throw new CharacterControllerException("Character name must be at least 2 characters");
        }

        // Set the Character's name with input string
        character.setName(name);
    }

    /**
     * Method to equip weapon.
     * Validity check is done within class to minimise calls required from this
     * controller class to the model class
     */
    public void equipWeapon(WeaponItem weapon) throws CharacterControllerException
    {
        try
        {
            // Equip imported weapon
            character.equipWeapon(weapon);
        }
        catch (CharacterPlayerException e)
        {
            throw new CharacterControllerException("Could not equip weapon - " +e.getMessage(), e);
        }
    }

    /**
     * Method to equip armour.
     * Validity check is done within class to minimise calls required from this
     * controller class to the model class
     */
    public void equipArmour(ArmourItem armour) throws CharacterControllerException
    {
        try
        {
            // Equip imported armour
            character.equipArmour(armour);
        }
        catch(CharacterPlayerException e)
        {
            throw new CharacterControllerException("Could not equip weapon - " + e.getMessage(), e);
        }
    }


}
