package controller.battle;

import controller.factory.EnemyFactory;
import model.item.potion.PotionItem;
import model.player.CombatPlayer;
import model.player.character.CharacterPlayer;
import model.player.character.CharacterPlayerException;
import model.player.enemy.EnemyPlayer;

/**
 * Controller class for the battle game mechanics:
 * - facilitate fighting
 * - facilitate using potion
 * - create enemy from the factory
 * - rewarding player for winning a battle
 */
public class BattleController
{
    private final EnemyFactory enemyFactory;

    public BattleController(EnemyFactory enemyFactory)
    {
        if(enemyFactory == null)
        {
            throw new IllegalArgumentException();
        }

        this.enemyFactory = enemyFactory;
    }

    /**
     * Method that facilitates the fighting between players.
     * Player 1 always attacks and Player 2 defends
     */
    public void fight(CombatPlayer p1, CombatPlayer p2)
    {
        p2.defend(p1.attack());
    }

    /**
     * Method that allows use of potion between character player and another player
     * - Exception is not checked
     *
     * NOTE - potions have been implemented so that defence can also block potion damage
     */
    public void usePotion(CharacterPlayer character, CombatPlayer player, PotionItem potion) throws InvalidBattleActionException {
        int damage = 0;
        try
        {
            damage = character.usePotion(potion);
            // If damage is 0, it is only for healing. If less than 0, invalid
            if (damage > 0)
            {
                player.defend(damage);
            }
        }
        catch (CharacterPlayerException e)
        {
            throw new InvalidBattleActionException("Cannot complete potion use - " + e.getMessage());
        }

    }

    /**
     * Method that spawns enemy using enemy factory.
     * Enemy factory class handles all spawning mechanics
     */
    public EnemyPlayer spawnEnemy()
    {
        EnemyPlayer enemy = enemyFactory.createEnemyRandomly();
        enemyFactory.updateStage();

        return enemy;
    }

    /**
     * Method that gives character player rewards.
     * Rewards are based on the gold and health of defeated enemy
     */
    public void givePlayerRewards(CharacterPlayer character, CombatPlayer enemy)
    {
        /* Give gold reward */
        double currentGold = character.getGold();
        double goldReward = enemy.getGold();
        character.setGold(currentGold + goldReward);

        /* Give health reward */
        int currentHealth = character.getHealth();
        double healthReward = (double)currentHealth * 1.5;
        character.setHealth((int)healthReward); // Reverted back to int since CombatPlayer setHealth() expects integer
    }
}
