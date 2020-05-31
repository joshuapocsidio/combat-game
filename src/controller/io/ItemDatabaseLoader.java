package controller.io;

import model.item.GameItem;

import java.util.List;

/**
 * Strategy Interface - ItemDatabaseLoader
 * - method load() for getting a list of items
 */
public interface ItemDatabaseLoader
{
    List<GameItem> load() throws InvalidItemDataSourceException;
}
