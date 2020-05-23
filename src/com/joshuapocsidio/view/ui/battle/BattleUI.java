package com.joshuapocsidio.view.ui.battle;

import com.joshuapocsidio.controller.battle.BattleController;
import com.joshuapocsidio.model.item.potion.PotionItem;
import com.joshuapocsidio.model.player.GamePlayer;
import com.joshuapocsidio.model.player.character.CharacterPlayer;
import com.joshuapocsidio.model.player.enemy.EnemyPlayer;
import com.joshuapocsidio.view.menu.MenuItem;
import com.joshuapocsidio.view.ui.UserInterface;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * View class for BattleUI and extends MenuItem
 *
 * This class is responsible for
 * - showing interface output specific for the battle mechanism of the game
 * - defining how the user interface will behave under this menu interface
 * - defining what is a valid input in between the turns of a battle
 */
public class BattleUI extends MenuItem implements AttackListener, DefendListener, PotionUseListener, HealListener, BattleOverListener, SpecialAbilityListener
{
    /** BattleUI Fields **/
    private final CharacterPlayer characterPlayer;
    private final BattleController battleController;

    private EnemyPlayer enemy;
    private boolean battleOver;

    /**
     * Constructor
     */
    public BattleUI(CharacterPlayer characterPlayer, BattleController battleController)
    {
        super();
        this.menuLabel = "Start Battle";

        this.characterPlayer = characterPlayer;
        this.battleController = battleController;
    }

    /**
     * Method is overridden so that whenever the parent class called to show ui:
     * - spawn a random enemy
     * - battleOver flag is reset to false
     * - initialise listeners
     */
    @Override
    public void show()
    {
        // Create enemy first
        enemy = battleController.spawnEnemy();

        // Reset battle over flag
        battleOver = false;

        // Initialise listeners
        this.initialiseListeners();

        // Show ui as usual
        super.show();
    }

    /**
     * Method to define the string output for the game's battle mechanics
     *
     * This method outputs the following:
     * - player information and attributes
     * - enemy information
     *
     * NOTE - UserInterface class is used to build the string output and returns the
     * built string structure based on components wished to include in the ui
     */
    @Override
    protected String getInterfaceOutput()
    {
        String menu_list = "1 - Attack!\n" + "2 - Use Potion!";


        UserInterface ui = new UserInterface.Builder()
                .withPreHeading("PLAYER INFO")
                .withPreInfo(characterPlayer.toString())
                .withHeading("BATTLEFIELD")
                .withBody(enemy.toString())
                .withFooter(menu_list)
                .withPrompt("Battle Announcer : How would you like to defeat the enemy? - ")
                .build();

        return ui.getOutput();
    }

    /**
     * Method to define the action to be taken if a valid user input is received.
     * This return false if there is an exception caught from the action implementation code
     * and passed back to MenuItem class to handle re-prompting user.
     *
     * This action implementation is as follows:
     * - user is prompted to choose from (1) attack or (2) use potion
     * - choosing attack will pass the task to battleController
     * - choosing potion, the user will be prompted which potion to use
     * - when a potion is chosen by user, task will be passed to the battleController
     * - in between turns, user is given an intermission and only goes ahead with the turn when a key is pressed
     * - the enemy will have its own turn after a key is pressed
     * - this process repeats until a player loses
     *
     * RETURN
     * - boolean    : indicates success of action
     */
    @Override
    protected boolean doAction(String choiceStr)
    {
        // Only continue if battle is not over
        if(!battleOver)
        {
            // Get user input
            Scanner input = new Scanner(System.in);
            int choice = Integer.parseInt(choiceStr);

            switch (choice)
            {
                case 1: // Attack
                    battleController.fight(characterPlayer, enemy);
                    break;
                case 2: // Use Potion
                    // Get all items from player inventory
                    List<PotionItem> potionItemList = characterPlayer.getPotions();

                    // If player has potions
                    if (potionItemList.size() > 0)
                    {
                        // Show user of available potions
                        System.out.println(characterPlayer.getPotionListString());
                        // Get chosen potion from user input
                        int potionChoice = Integer.parseInt(input.nextLine());
                        // Attempt to user potion
                        battleController.usePotion(characterPlayer, enemy, potionItemList.get(potionChoice - 1));
                    }
                    else
                    {
                        System.out.println("You have no potions - Please try again");
                        return false;
                    }
                    break;
                default: // Invalid choice try again
                    System.out.println("Invalid choice - Please try again");
                    return false;
            }
        }
        else
        {
            return true;
        }

        // Intermission to allow user to read and understand the flow/pace of the game
        this.pressAnyKeyToContinue();

        // Only continue if battle is not over
        if(!battleOver)
        {
            battleController.fight(enemy, characterPlayer); // enemy attacks player
        }
        else
        {
            return true;
        }

        return battleOver;
    }

    /**
     * Method which defines what kind if user input is valid
     *
     * This method checks if user input is within the valid range of integer
     * values. Additional check is used since leaving the battle is prohibited.
     *
     * RETURN
     * - boolean        : indicates validity of user input
     */
    @Override
    protected boolean isValid(String choiceStr)
    {
        try
        {
            // Attempt to convert string to integer
            int choice = Integer.parseInt(choiceStr);

            // If user choice is exit
            if(choice == 0)
            {
                // User cannot exit this menu interface like other ui's
                System.out.print("You cannot flee during a battle! ");
                return false;
            }
            else if(choice > 2 || choice < 1)
            {
                System.out.print("Input is out of bounds - ");
                return false;
            }

            if(choice == 2 && characterPlayer.getPotions().size() == 0)
            {
                System.out.print("You have no potions - ");
                return false;
            }

            return true;
        }
        catch(NumberFormatException e)
        {
            System.out.print("Input must be an integer - ");
            return false;
        }
    }

    /**
     * Method to initialise this as listener for following:
     * - attack listener for attack events for both character and enemy players
     * - defend listener for defend events for both character and enemy players
     * - potion use listener for potion use events for character players
     * - heal listener for heal events for character players
     * - battle over listener for when the battle is over (when a player loses)
     * - special ability listener for when an enemy's special ability triggers
     */
    private void initialiseListeners()
    {
        // Add itself as listener for attack, defend, and use potion events to player
        characterPlayer.addAttackListener(this);
        characterPlayer.addDefendListener(this);
        characterPlayer.addPotionUseListener(this);
        characterPlayer.addHealListener(this);
        characterPlayer.addBattleOverListener(this);

        // Add itself as listener for attack and defend to enemy
        enemy.addAttackListener(this);
        enemy.addDefendListener(this);
        enemy.addBattleOverListener(this);
        enemy.addSpecialAbilityListener(this);
    }

    /**
     * Method to detach itself from listening for events
     *
     * This method is called when battle is over
     */
    private void detachListeners()
    {
        // Add itself as listener for attack, defend, and use potion events to player
        characterPlayer.removeAttackListener(this);
        characterPlayer.removeDefendListener(this);
        characterPlayer.removePotionUseListener(this);
        characterPlayer.removeHealListener(this);
        characterPlayer.removeBattleOverListener(this);

        // Add itself as listener for attack and defend to enemy
        enemy.removeAttackListener(this);
        enemy.removeDefendListener(this);
        enemy.removeBattleOverListener(this);
        enemy.removeSpecialAbilityListener(this);
    }

    /**
     * Observer Method to notify user of attack event
     * - shows who attacked
     * - shows output damage before defence calculations
     */
    @Override
    public void showAttackEvent(GamePlayer player, int damage)
    {
        System.out.println();
        System.out.println("-------- BATTLE ANNOUNCER --------");
        System.out.println(player.getName() + " attacked for " + damage + " damage!!");
    }

    /**
     * Observer Method to notify user of defend event
     * - shows who defended
     * - shows damaged blocked
     * - shows amount of damage taken (if any)
     * - shows the health of the player who defended the incoming damage
     * - shows if the player who defended did not take any damage
     */
    @Override
    public void showDefendEvent(GamePlayer player, int blocked, int damageTaken)
    {
        System.out.println();
        System.out.println("-------- BATTLE ANNOUNCER --------");
        if(blocked != 0)
        {
            System.out.println(player.getName() + " defended " + blocked + " damage!!");
        }

        if(damageTaken != 0)
        {
            System.out.println(player.getName() + " took " + damageTaken + " damage!!");
            System.out.println(player.getName() + " has " + (Math.max(player.getHealth() - damageTaken, 0)) + " life points left!");
        }
        else
        {
            System.out.println(player.getName() + " took no damage!!");
        }

    }

    /**
     * Observer Method to notify user of potion use event
     * - shows who used a potion
     * - shows output damage before defence calculations (if any)
     */
    @Override
    public void showPotionUseEvent(GamePlayer player, String name, int damage)
    {
        System.out.println();
        System.out.println("-------- BATTLE ANNOUNCER --------");
        if(damage > 0)
        {
            System.out.println(player.getName() + " used " + name + " to deal " + damage + " damage!!");
        }
        else
        {
            System.out.println(player.getName() + " used " + name + "!!");
        }
    }

    /**
     * Observer Method to notify user of heal event
     * - shows which player healed
     * - shows amount of life points healed
     */
    @Override
    public void showHealEvent(GamePlayer player, int healed)
    {
        System.out.println();
        System.out.println(player.getName() + " healed for " + healed + " life points!!");
    }

    /**
     * Observer Method to notify user that the battle has ended
     * - shows who won
     * - shows the rewards
     *
     * This method also calls the battle controller to give the player
     * the rewards from winning the battle (if applicable)
     */
    @Override
    public void endBattle(GamePlayer deadPlayer)
    {
        System.out.println();
        System.out.println("- - - - POST-BATTLE RESULTS - - - -");

        // If the player who lost is not the character player
        if(!deadPlayer.getName().equals(characterPlayer.getName()))
        {
            System.out.println("          Winner : " +characterPlayer.getName());
            System.out.println("- - - - - -  REWARD(S)  - - - - - -");
            System.out.println("            Gold : " + enemy.getGold());
            System.out.println("            HP   : " +( (characterPlayer.getHealth() * 1.5) - characterPlayer.getHealth()));

            // Give player rewards
            battleController.givePlayerRewards(characterPlayer, enemy);
        }
        else
        {
            System.out.println("Winner : " +enemy.getName());
        }

        this.battleOver = true;
        this.detachListeners();
        this.terminate();
    }

    /**
     * Observer Method to notify user of a special ability event
     * - shows the name of the enemy whose special ability triggered
     * - shows the description of the special ability
     */
    @Override
    public void showSpecialAbilityEvent(EnemyPlayer enemy, String description)
    {
        System.out.println();
        System.out.println("* * * * SPECIAL ABILITY * * * *");
        System.out.println(centreJustify(enemy.getName()));
        System.out.println(centreJustify(description));
        System.out.println("* * * * * * * * * * * * * * * *");
    }

    /**
     * Method to centre justify any given string
     */
    private String centreJustify(String str)
    {
        int strWidth = 32;
        int textWidth = str.length();

        int leftPadding = (strWidth/2) + (textWidth/2);
        int rightPadding = strWidth;

        str = String.format("%" +leftPadding+ "s", str);
        str = String.format("%-" +rightPadding+ "s", str);

        return str;
    }

    /**
     * Method to wait for user to press enter to continue
     *
     * This allows the user to be able to think through the flow of the
     * battle and keep up with the pace.
     */
    private void pressAnyKeyToContinue()
    {
        System.out.println();
        System.out.println("Press ENTER to continue...");
        try
        {
            System.in.read();
        }
        catch(IOException e)
        {
            System.out.println("Please try again");
        }
    }
}

