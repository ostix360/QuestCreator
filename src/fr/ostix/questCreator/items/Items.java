package fr.ostix.questCreator.items;

import java.util.*;

public class Items {

    private static final HashMap<Integer, Item> items = new HashMap<>();

    public static Item potion = registerItem(new ItemPotion(0));


    private static Item registerItem(Item i) {
        items.put(i.getId(), i);
        return i;
    }


    public static Item getItem(int id) {
        return items.get(id);
    }
}
