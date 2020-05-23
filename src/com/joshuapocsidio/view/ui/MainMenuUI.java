package com.joshuapocsidio.view.ui;

import com.joshuapocsidio.model.player.character.CharacterPlayer;
import com.joshuapocsidio.view.menu.Menu;

/**
 * View class for MainMenuUI and extends Menu
 *
 * This class is responsible for
 * - showing interface output specific for options at the top level of the game
 */
public class MainMenuUI extends Menu implements GameOverListener
{
    /** MainMenuUI Fields **/
    private final CharacterPlayer characterPlayer;

    /**
     * Constructor
     */
    public MainMenuUI(CharacterPlayer characterPlayer)
    {
        super();

        this.characterPlayer = characterPlayer;

        this.initialiseListeners();
    }

    /**
     * Method to initialise this as listener for following:
     * - game over listener for when the character player loses and game ends
     */
    private void initialiseListeners()
    {
        // Add itself as listener for attack, defend, and use potion events to player
        characterPlayer.addGameOverListener(this);
    }

    /**
     * Method to define the string output for the main menu options
     *
     * This method outputs the following:
     * - player information and attributes
     * - list of options available
     *
     * NOTE - UserInterface class is used to build the string output and returns the
     * built string structure based on components wished to include in the ui
     */
    @Override
    protected String getInterfaceOutput()
    {
        UserInterface ui = new UserInterface.Builder()
                .withPreHeading("PLAYER ATTRIBUTES")
                .withPreInfo(characterPlayer.toString())
                .withHeading("MAIN MENU")
                .withSubHeading("Welcome to the Curtin Arena!")
                .withBody(this.getMenuList())
                .withPrompt("What would you like to do?")
                .build();

        return ui.getOutput();
    }

    /**
     * Observer method for terminating the game when the game ends
     *
     * Since this class is the very root of the game hierarchy of menu interfaces,
     * when this calls terminate, all the menu interfaces within the game will call terminate
     * to ensure that all interfaces are ended
     */
    @Override
    public void endGame()
    {
        this.terminate();

        System.out.println("\n\n\n");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("* * * * * * * * * GAME OVER * * * * * * * *");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
    }
}
