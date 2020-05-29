package view.ui;

import model.player.character.CharacterPlayer;
import model.player.character.GameOverObserver;
import view.menu.MenuDirectory;

/**
 * View class for MainMenuDirectory and extends Menu
 *
 * This class is responsible for
 * - showing interface output specific for options at the top level of the game
 */
public class MainMenuDirectory extends MenuDirectory implements GameOverObserver
{
    /** MainMenuDirectory Fields **/
    private final CharacterPlayer characterPlayer;

    /**
     * Constructor
     */
    public MainMenuDirectory(CharacterPlayer characterPlayer)
    {
        super();

        this.characterPlayer = characterPlayer;

        this.initialiseObservers();
    }

    /**
     * Method to initialise this as Observer for following:
     * - game over Observer for when the character player loses and game ends
     */
    private void initialiseObservers()
    {
        // Add itself as Observer for attack, defend, and use potion events to player
        characterPlayer.addGameOverObserver(this);
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
                .withBody(this.getMenuListString())
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
    public void showEndGame()
    {
        this.terminate();

        System.out.println("\n\n\n");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("* * * * * * * * * GAME OVER * * * * * * * *");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
    }
}
