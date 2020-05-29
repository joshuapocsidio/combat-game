package controller.factory;

import model.player.enemy.*;

/**
 * Factory class for creating and spawning enemies.
 * Every time an enemy is created and spawned, probabilities vary
 * - Slime starts at 50 and decreases by 5 each stage
 * - Goblin starts at 30 and decreases by 5 each stage
 * - Ogre starts at 20 and decreases by 5 each stage
 * - Dragon starts at 0 and increases by 15 each stage
 */
public class EnemyFactory
{
    private int stage;

    public EnemyFactory()
    {
        this.stage = 0;
    }

    /**
     * Create enemy randomly based on pre-defined probabilities and algorithms
     */
    public EnemyPlayer createEnemyRandomly()
    {
        // Set spawn probabilities
        int slimeProbability = 50 - (5 * stage);
        int goblinProbability = 30 - (5 * stage);
        int ogreProbability = 20 - (5 * stage);
        int dragonProbability = (15 * stage);

        // Get a number between 0 - 99
        int num = (int)(Math.random() * 100.0);

        String enemyName = "";

        // Slime Example : (50%) 0 - 49, (45%) 0 - 44, (35%) 0 - 39, ...
        if(num < (slimeProbability) )
        {
            enemyName = "slime";
        }
        // Goblin Example : (30%) 50 - 79, (25%) 45 - 69, (20%) 39 - 59, ...
        else if(num < (goblinProbability + slimeProbability) )
        {
            enemyName = "goblin";
        }
        // Ogre Example : (20%) 80 - 99, (15%) 70 - 84, (10%) 60 - 69, ...
        else if(num < (ogreProbability + goblinProbability + slimeProbability) )
        {
            enemyName = "ogre";
        }
        // Ogre Example : (0%) - , (15%) 85 - 99, (20%) 70 - 99, ...
        else if(num < (dragonProbability + ogreProbability + goblinProbability + slimeProbability) )
        {
            enemyName = "dragon";
        }

        return this.createEnemy(enemyName);
    }

    /**
     * Creates specific enemy based on name string
     */
    private EnemyPlayer createEnemy(String enemyName)
    {
        EnemyPlayer enemy = null;

        switch(enemyName.toLowerCase())
        {
            case "slime":
                enemy = new SlimeEnemy();
                break;
            case "goblin":
                enemy = new GoblinEnemy();
                break;
            case "ogre":
                enemy = new OgreEnemy();
                break;
            case "dragon":
                enemy = new DragonEnemy();
                break;
            default:
                break;
        }

        return enemy;
    }

    /**
     * Updates current stage to next
     */
    public void updateStage()
    {
        this.stage++;
    }

    /**
     * Resets the stage back to zero for future use
     * For example - if game doesn't end when player loses
     */
    public void resetStage()
    {
        this.stage = 0;
    }
}
