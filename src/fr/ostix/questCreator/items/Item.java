package fr.ostix.questCreator.items;

public class Item {
    private final int id;
    private final String name;

    public Item(String name,int id) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
