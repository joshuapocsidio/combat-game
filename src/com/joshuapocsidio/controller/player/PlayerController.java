package com.joshuapocsidio.controller.player;

import com.joshuapocsidio.model.player.GamePlayerException;
import com.joshuapocsidio.model.item.armour.ArmourItem;
import com.joshuapocsidio.model.item.weapon.WeaponItem;
import com.joshuapocsidio.model.player.character.CharacterPlayer;
import com.joshuapocsidio.model.player.character.CharacterPlayerException;

/**
 * Controller class for using player methods and functions
 * - set player name
 * - equip weapon/armour
 */
public class PlayerController
{
    private final CharacterPlayer player;

    public PlayerController(CharacterPlayer player)
    {
        if(player == null)
        {
            throw new IllegalArgumentException("Player cannot be null");
        }

        this.player = player;
    }

    /**
     * Method to set the player name and checks validity of input name
     */
    public void setPlayerName(String name) throws PlayerControllerException
    {
        if(name.isEmpty() || name.isBlank())
        {
            throw new PlayerControllerException("Player name must not be empty nor blank");
        }

        if(name.length() < 3)
        {
            throw new PlayerControllerException("Player name must be at least 2 characters");
        }

        // Set the player's name with input string
        player.setName(name);
    }

    /**
     * Method to equip weapon.
     * Validity check is done within class to minimise calls required from this
     * controller class to the model class
     */
    public void equipWeapon(WeaponItem weapon) throws PlayerControllerException
    {
        try
        {
            // Equip imported weapon
            player.equipWeapon(weapon);
        }
        catch (CharacterPlayerException e)
        {
            throw new PlayerControllerException("Could not equip weapon - " +e.getMessage(), e);
        }
    }

    /**
     * Method to equip armour.
     * Validity check is done within class to minimise calls required from this
     * controller class to the model class
     */
    public void equipArmour(ArmourItem armour) throws PlayerControllerException
    {
        try
        {
            // Equip imported armour
            player.equipArmour(armour);
        }
        catch(CharacterPlayerException e)
        {
            throw new PlayerControllerException("Could not equip weapon - " + e.getMessage(), e);
        }
    }


}
