package model.item;

import controller.factory.InvalidItemFactoryException;
/**
 * Observer Interface - ItemDatabaseChangeObserver
 * - method addNewItem() overridden by observers
 * - observers typically are database managers which have access to the current game state
 * - if any external servers/databases/classes are required to update the game shop during run-time
 * - then subjects must implement methods to notify ItemDatabaseChangeObservers
 */
public interface ItemDatabaseChangeObserver
{
    void addNewItem(String type, String name, int minEffect, int maxEffect, int cost, String[] attributes) throws InvalidItemFactoryException, InvalidItemDatabaseException;
}
