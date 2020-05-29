package model.player.character;

import model.item.armour.ArmourItem;
import model.item.GameItem;
import model.item.potion.PotionItem;
import model.item.weapon.WeaponItem;
import model.player.CombatPlayer;

import java.util.*;

/**
 * Model class for CharacterPlayer inheriting from CombatPlayer abstract class
 *
 * FIELDS
 * - inventory      : List of GameItem
 * - equippedWeapon : WeaponItem
 * - equippedArmour : ArmourItem
 *
 * ObserverS/OBSERVERS
 * - potionUseObservers : List of PotionUseObserver
 * - gameOverObservers  : List of GameOverObserver
 */
public class CharacterPlayer extends CombatPlayer
{

    /** Character Player specific Fields **/
    private List<GameItem> inventory;
    private WeaponItem equippedWeapon;
    private ArmourItem equippedArmour;

    private int equippedWeaponIndex;
    private int equippedArmourIndex;
    /** List of observers **/
    private List<PotionUseObserver> potionUseObservers;
    private List<GameOverObserver> gameOverObservers;

    /**
     * DEFAULT CONSTRUCTOR
     * - Sets up default character player
     * - Name       : "Player"
     * - MaxHealth  : 30
     * - Gold       : 100
     * **/
    public CharacterPlayer()
    {
        super("Player", 30, 100);

        inventory = new LinkedList<>();

        equippedWeapon = null;
        equippedArmour = null;

        potionUseObservers = new LinkedList<>();
        gameOverObservers = new LinkedList<>();
    }

    /**
     * CONSTRUCTOR - without weapon and armour but with relevant fields
     *
     * NOTE : This constructor is created for future use in case the game changes so that it starts
     * with the user constructing their own customised character. Perhaps, having different races and
     * different types of starting classes can vary the starting attributes and stats of the player
     * **/
    public CharacterPlayer(String inName, int inMaxHealth, double inGold)
    {
        super(inName, inMaxHealth, inGold);
    }

    /**
     * CONSTRUCTOR - with weapon and armour
     * - Sets up default character player
     * - Name       : "Player"
     * - MaxHealth  : 30
     * - Gold       : 100
     *
     * NOTE : This constructor is created so that the main does not need to catch such exception
     * since there is no need to check if a weapon/armour is already equipped when initialising
     * a completely new CharacterPlayer
     * **/
    public CharacterPlayer(WeaponItem weapon, ArmourItem armour)
    {
        super("Player", 30, 100);

        inventory = new LinkedList<>();

        inventory.add(weapon);
        inventory.add(armour);
        equippedWeapon = weapon;
        equippedWeaponIndex = 0;
        equippedArmour = armour;
        equippedArmourIndex = 1;

        potionUseObservers = new LinkedList<>();
        gameOverObservers = new LinkedList<>();
    }

    /**
     * CONSTRUCTOR - with all relevant fields
     * - Sets up player with imported fields
     * - Name       : "Player"
     * - MaxHealth  : 30
     * - Gold       : 100
     *
     * NOTE : This constructor is created for future use in case the game changes so that it starts
     * with the user constructing their own customised character. Perhaps, having different races and
     * different types of starting classes can vary the starting attributes and stats of the player
     */
    public CharacterPlayer(String inName, int inMaxHealth, double inGold, WeaponItem inEquippedWeapon, ArmourItem inEquippedArmour)
    {
        super(inName, inMaxHealth, inGold);

        inventory = new LinkedList<>();

        inventory.add(inEquippedArmour);
        inventory.add(inEquippedWeapon);
        equippedWeapon = inEquippedWeapon;
        equippedWeaponIndex = 0;
        equippedArmour = inEquippedArmour;
        equippedArmourIndex = 1;

        potionUseObservers = new LinkedList<>();
        gameOverObservers = new LinkedList<>();
    }

    /**
     * Method for calculating the attack damage.
     *
     * RETURN
     * - calculated attack damage : int
     *
     * NOTE - This method is required by the CombatPlayer parent class which holds the method for
     * the attack functionality. For this subclass, the weapon class strike() method determines
     * the damage calculation.
     */
    @Override
    protected int calculateAttack()
    {
        return equippedWeapon.strike();
    }

    /**
     * Method for calculating the blocked damage.
     *
     * RETURN
     * - calculated blocked damage : int
     *
     * NOTE - This method is required by the CombatPlayer parent class which holds the method for
     * the defend functionality. For this subclass, the weapon class block() method determines
     * the block calculation.
     */
    @Override
    protected int calculateDefence(int damage)
    {
        return equippedArmour.block();
    }

    /**
     * Method for setting health of player subclass
     *
     * NOTE - This method is overridden but still calls the parent class setHealth(int).
     * This is implemented since GameOverObservers are only concerned with the death of
     * the character player. Game only ends when main character dies.
     */
    @Override
    public void setHealth(int health)
    {
        super.setHealth(health);

        if(this.getHealth() == 0)
        {
            notifyGameOverObservers();
        }
    }

    /**
     * Method for potion use functionality
     * - checks if item matches any item in inventory
     * - consumes that potion and then removes from inventory
     * - notifies Observers of potion use event
     *
     * RETURN
     * - damage effect : int
     *
     * NOTE - Damage effect as integer is returned as this will allow for potions to deal
     * damage in the same way attacking does. More on this inside implementations of potion class.
     */
    public int usePotion(PotionItem potion)
    {
        for(GameItem item : inventory)
        {
            if(item.getName().equals(potion.getName()))
            {
                notifyPotionUseObservers(potion.getName());

                int effect;
                effect = potion.use(this);

                inventory.remove(item);

                return effect;
            }
        }
        return 0;
    }

    /**
     * Method for checking if item exists in inventory
     */
    public boolean hasItem(GameItem item)
    {
        return inventory.contains(item);
    }

    /** ACCESSORS */
    public WeaponItem getEquippedWeapon()
    {
        return equippedWeapon;
    }

    public ArmourItem getEquippedArmour()
    {
        return equippedArmour;
    }

    public GameItem getItem(int index) throws CharacterPlayerException
    {
        if(index < inventory.size() && index >= 0)
        {
            return inventory.get(index);
        }
        else
        {
            throw new CharacterPlayerException("Inventory index is out of bounds", new IndexOutOfBoundsException());
        }
    }

    public GameItem getItem(GameItem item) throws CharacterPlayerException
    {
        if(inventory.contains(item))
        {
            // Only get item if inventory contains this item
            return inventory.get(inventory.indexOf(item));
        }
        else
        {
            throw new CharacterPlayerException("Item does not exist in inventory"); // TODO : Update the cause of this exception
        }
    }

    public List<GameItem> getInventory()
    {
        return Collections.unmodifiableList(inventory);
    }

    public List<WeaponItem> getWeapons()
    {
        List<WeaponItem> weapons = new LinkedList<>();
        for(GameItem item : inventory)
        {
            if(item instanceof WeaponItem)
            {
                weapons.add((WeaponItem) item);
            }
        }
        return weapons;
    }

    public List<ArmourItem> getArmours()
    {
        List<ArmourItem> armours = new LinkedList<>();
        for(GameItem item : inventory)
        {
            if(item instanceof ArmourItem)
            {
                armours.add((ArmourItem) item);
            }
        }
        return armours;
    }

    public List<PotionItem> getPotions()
    {
        List<PotionItem> potions = new LinkedList<>();
        for(GameItem item : inventory)
        {
            if(item instanceof PotionItem)
            {
                potions.add((PotionItem) item);
            }
        }
        return Collections.unmodifiableList(potions);
    }

    /** MUTATORS */
    public void addToInventory(GameItem item)
    {
        if(item == null)
        {
            throw new IllegalArgumentException("Item cannot be null");
        }

        inventory.add(item);
    }

    /**
     * Method for removing items from inventory
     * - Checks items inside inventory
     * - if current index does not match equipped weapon/armour index, check further
     * - if item matches then remove that item
     * - update the equipped weapon/armour indices appropriately
     *
     * NOTE - The way this is implemented is due to the issue of attempting to sell items that are identical
     * to weapon/armour that are currently equipped. Since items can have completely identical contents,
     * name, and stats, index pointing to the equipped items in inventory is required.
     */
    public void removeFromInventory(GameItem item) throws CharacterPlayerException
    {
        boolean found = false;
        int i = 0;

        int numMatch = 0;

        // Iterate through inventory until a valid item is found
        while(!found && i < inventory.size())
        {
            // Check if item matches
            if (item.equals(inventory.get(i)))
            {
                // Only check further if current index does not match equipped armour and equipped weapon index
                if(i != equippedArmourIndex && i != equippedWeaponIndex)
                {
                    found = true;

                    // Update equipped weapon/armour indices
                    if(i < equippedArmourIndex)
                    {
                        equippedArmourIndex--;
                    }

                    if(i < equippedWeaponIndex)
                    {
                        equippedWeaponIndex--;
                    }

                    inventory.remove(i);
                }
                numMatch++;
            }
            i++;
        }

        if(numMatch == 0)
        {
            throw new CharacterPlayerException("Item is not found in inventory");
        }
        else if(numMatch == 1 && !found)
        {
            throw new CharacterPlayerException("Item is currently equipped");
        }
    }

    /**
     * Method for equipping weapon
     * - checks if weapon is null
     * - checks if player owns weapon and found in inventory
     * -
     *
     * NOTE - This only equips if weapon is already found inside the inventory. This also
     *
     */
    public void equipWeapon(WeaponItem weapon) throws CharacterPlayerException
    {
        if(weapon == null)
        {
            throw new CharacterPlayerException("Weapon must not be null");
        }

        if(!this.hasItem(weapon))
        {
            throw new CharacterPlayerException("Player does not own this weapon");
        }

        boolean found = false;
        int i = 0;
        while(!found && i < inventory.size())
        {
            if(weapon.equals(inventory.get(i)))
            {
                found = true;
                this.equippedWeapon = weapon;
                this.equippedWeaponIndex = i;
            }
            i++;
        }
    }

    /**
     * Method for equipping armour
     * - checks if armour is null
     * - checks if player owns armour and found in inventory
     *
     * NOTE - This only equips if armour is already found inside the inventory
     */
    public void equipArmour(ArmourItem armour) throws CharacterPlayerException
    {
        if(armour == null)
        {
            throw new CharacterPlayerException("Armour must not be null");
        }

        if(!this.hasItem(armour))
        {
            throw new CharacterPlayerException("Player does not own this armour");
        }

        boolean found = false;
        int i = 0;
        while(!found && i < inventory.size())
        {
            if(armour.equals(inventory.get(i)))
            {
                found = true;
                this.equippedArmour = armour;
                this.equippedArmourIndex = i;
            }
            i++;
        }
    }

    @Override
    public String toString()
    {
        String inventoryString = "";

        int index = 1;
        for(GameItem item : inventory)
        {
            inventoryString += "\n" +index+ "  -  " +item.toString();
            index++;
        }
        return super.toString()+
                "\nWeapon    : " +equippedWeapon.toString()+
                "\nArmour    : " +equippedArmour.toString()+
                "\nInventory : " +inventoryString;
    }

    /**
     * Method for obtaining string representation of weapons owned by this player
     */
    public String getWeaponListString()
    {
        String list = "\n";

        int index = 1;
        for(WeaponItem weapon : this.getWeapons())
        {
            list = list + index + " - " +weapon.toString() + "\n";
            index++;
        }

        return list;
    }

    /**
     * Method for obtaining string representation of armours owned by this player
     */
    public String getArmourListString()
    {
        String list = "\n";

        int index = 1;
        for(ArmourItem armour : this.getArmours())
        {
            list = list + index + " - " +armour.toString() + "\n";
            index++;
        }

        return list;
    }

    /**
     * Method for obtaining string representation of potions owned by this player
     */
    public String getPotionListString()
    {
        String list = "\n";
        int index = 1;
        for(PotionItem potion : this.getPotions())
        {
            list = list + index + " - " +potion.toString() + "\n";
            index++;
        }

        return list;
    }

    /**
     * Method for obtaining string representation of items owned by this player
     */
    public String getItemListString()
    {
        String list = "\n";

        int index = 1;
        for(GameItem item : this.getInventory())
        {
            list = list + index + " - " +item.toString() + "\n";
            index++;
        }

        return list;
    }

    /**
     * Methods for adding, removing, and notifying PotionUseObservers
     */
    public void addPotionUseObserver(PotionUseObserver potionUseObserver)
    {
        potionUseObservers.add(potionUseObserver);
    }

    public void removePotionUseObserver(PotionUseObserver potionUseObserver)
    {
        potionUseObservers.remove(potionUseObserver);
        // TODO : Throw exception if battle obs does not exist
    }

    public void notifyPotionUseObservers(String name)
    {
        for(PotionUseObserver potionUseObserver : potionUseObservers)
        {
            potionUseObserver.showPotionUseEvent(this, name);
        }
    }

    /**
     * Methods for adding, removing, and notifying GameOverObservers
     */
    public void addGameOverObserver(GameOverObserver gameOverObserver)
    {
        gameOverObservers.add(gameOverObserver);
    }

    public void removeGameOverObserver(GameOverObserver gameOverObserver)
    {
        gameOverObservers.remove(gameOverObserver);
        // TODO : Throw exception if battle obs does not exist
    }

    public void notifyGameOverObservers()
    {
        for(GameOverObserver gameOverObserver : gameOverObservers)
        {
            gameOverObserver.showEndGame();
        }
    }
}
