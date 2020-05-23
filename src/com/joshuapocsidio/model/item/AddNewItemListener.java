package com.joshuapocsidio.model.item;

import com.joshuapocsidio.controller.factory.InvalidItemFactoryException;

public interface AddNewItemListener
{
    void createNewItem(String type, String name, int minEffect, int maxEffect, int cost, String[] attributes) throws InvalidItemFactoryException, InvalidItemDatabaseException;
}
