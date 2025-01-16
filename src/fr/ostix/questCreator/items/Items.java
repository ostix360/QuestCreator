package fr.ostix.questCreator.items;

import java.util.*;

public class Items {

    private static final HashMap<Integer, Item> items = new HashMap<>();

    public static Item potion = registerItem(new ItemPotion("Potion",0));
    public static Item privateDiary = registerItem(new ItemPrivateDiary("Private Diary",1));


    private static Item registerItem(Item i) {
        items.put(i.getId(), i);
        return i;
    }


    //Return list of Items

    public static Collection<Item> getItems() {
        return items.values();
    }

    public static Item getItem(int id) {
        return items.get(id);
    }
}
