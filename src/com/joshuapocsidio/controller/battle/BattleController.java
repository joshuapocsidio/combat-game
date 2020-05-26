package com.joshuapocsidio.controller.battle;

import com.joshuapocsidio.controller.factory.EnemyFactory;
import com.joshuapocsidio.model.item.potion.PotionItem;
import com.joshuapocsidio.model.player.CombatPlayer;
import com.joshuapocsidio.model.player.character.CharacterPlayer;
import com.joshuapocsidio.model.player.enemy.EnemyPlayer;

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
     * Method that allows use of potion between character player and another player.
     * Damage from potion is subtracted in case the potion is of damage type
     * Healing potions should return 0 and therefore have no effect on other player
     *
     * NOTE - potion use does not throw exceptions since potion objects are created
     * using item factory with relevant constraints to prevent damage being negative.
     */
    public void usePotion(CharacterPlayer p1, CombatPlayer p2, PotionItem potion)
    {
        int damage = p1.usePotion(potion);

        // If damage is 0, it is only for healing. If less than 0, invalid
        if (damage > 0)
        {
            //p2.setHealth(p2.getHealth() - damage);
            p2.defend(damage);
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
    public void givePlayerRewards(CharacterPlayer player, CombatPlayer enemy)
    {
        /* Give gold reward */
        player.setGold(player.getGold() + enemy.getGold());

        /* Give health reward */ // TODO : COMMENT ON THIS
        player.setHealth((int)((double)player.getHealth() * 1.5));
    }
}
