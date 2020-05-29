package model.item;

import controller.factory.InvalidItemFactoryException;

public interface ItemDatabaseChangeObserver
{
    void addNew(String dataEntry);
    void addNewItem(String type, String name, int minEffect, int maxEffect, int cost, String[] attributes) throws InvalidItemFactoryException, InvalidItemDatabaseException;
}
