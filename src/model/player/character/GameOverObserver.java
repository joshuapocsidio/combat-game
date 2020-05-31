package model.player.character;

/**
 * Observer Interface - GameOverObserver
 * - method showEndGame() for displaying event when full game is over
 * - may also handle how view classes will behave
 */
public interface GameOverObserver
{
    void showEndGame();
}
